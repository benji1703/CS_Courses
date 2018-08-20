import numpy as np
import tensorflow as tf

### The following line is the only place in this code where you need to modify things.
arch = [100, 20, 1]
### The above architecture defines a one-hidden layer with 100 neurons each with a ReLU activation, and a single output neuron with a linear activation

def read_data():
    data = np.load('l1train.npz')
    X = data['X']
    y = data['y']
    return X, y

def read_valid():
    valid = np.load('l1valid.npz')
    Xvalid = valid['X']
    yvalid = valid['y']
    return Xvalid, yvalid


class SequentialIterator(object):
    def __init__(self, X, y, batch_size):
        self._X, self._y = X, y
        self._batch_size = batch_size
        self._i = 0
        self._max_i = X.shape[0]

    def __iter__(self):
        return self

    def next(self):
        return self.__next__()

    def __next__(self):
        if self._i == self._max_i:
            raise StopIteration()
        end_i = min(self._i + self._batch_size, self._max_i)
        locations = range(self._i, end_i)
        self._i = end_i
        return self._X[locations, :], self._y[locations]



class CyclicDataIter(object):
    def __init__(self, X, y, batch_size):
        self._X, self._y = X, y
        self._batch_size = batch_size
        self._i = 0
        self._max_i = X.shape[0]

    def __iter__(self):
        return self

    def next(self):
        return self.__next__()

    def __next__(self):
        end_i = self._i + self._batch_size

        if end_i <= self._max_i:
            locations = range(self._i, end_i)
            self._i = end_i

        else:
            locations = list(range(self._i, self._max_i))
            self._i = end_i % self._max_i
            locations.extend(range(self._i))
        self._i = end_i % self._max_i
        return self._X[locations, :], self._y[locations]


### init parameters
X, y = read_data()
Xvalid, Yvalid = read_valid()

#y = np.ones(X.shape[0])
num_examples_train = X.shape[0]
d = X.shape[1]

### hyper-parameters
activation = tf.nn.relu



print('%d layers:' % len(arch))
print('First layer size: %d' % arch[0])
print('Data dimension: %d' % d)

w_mean = 0.0
#w_std = 0.01
learning_rate = tf.Variable(initial_value=0.1, name='learning_rate')
num_iterations = 5000 * 40 + 1
#radius = 1.
batch_size = 100

### init network weights
data = tf.placeholder(dtype=tf.float32,
                      shape=[None, d])
weights = []
biases = []
layers = []

curr_layer = data
input_dim = d
for i, out_dim in enumerate(arch, start=1):
    W = tf.Variable(initial_value=tf.random_normal(shape=[input_dim, out_dim],
                                                   mean=w_mean,
                                                   stddev=0.1/np.sqrt(input_dim)),
                    dtype=tf.float32)

    b = tf.Variable(initial_value=tf.constant(0.0, shape=[out_dim]),
                    dtype=tf.float32)
    input_dim = out_dim
    curr_layer = tf.matmul(curr_layer, W) + b
    if i < len(arch):
        curr_layer = activation(curr_layer)

    weights.append(W)
    biases.append(b)
    layers.append(curr_layer)


predictions = tf.reshape(layers[-1], [-1])
true_y = tf.placeholder(dtype=tf.float32,
                        shape=[None])

loss_tensor = tf.reduce_mean(tf.pow(predictions - true_y, 2))

#learner = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss_tensor)
batch = tf.Variable(0)

# Use simple momentum for the optimization.
learner = tf.train.MomentumOptimizer(learning_rate,0.95).minimize(loss_tensor, global_step=batch)
learning_rate_updater = tf.assign(learning_rate, learning_rate*0.95)
#learner = tf.train.MomentumOptimizer(learning_rate, 0.95).minimize(loss_tensor)

init = tf.global_variables_initializer()


def evaluation_in_batches(X, y, sess, target_tensor):
    results = [sess.run(target_tensor, feed_dict={data: batch_x, true_y: batch_y})
               for batch_x, batch_y in SequentialIterator(X,y, 1000)]
    return np.mean(results)



with tf.Session() as sess:
    init.run()
    iterator = CyclicDataIter(X, y, batch_size)

    for i in range(num_iterations):
        batch_x, batch_y = next(iterator)
        _, curr_loss, lr = sess.run([learner, loss_tensor, learning_rate], feed_dict={data: batch_x, true_y: batch_y})
        if i % 1000 == 0:
            #print('now on step number %s with loss: %s' % (i, curr_loss))
            global_train_loss = evaluation_in_batches(X, y, sess, loss_tensor)
            valid_loss = evaluation_in_batches(Xvalid, Yvalid, sess, loss_tensor)
            print('Training err: %.9s, validation err: %.9s (step=%06d)' % (global_train_loss, valid_loss, i))
            #print('loss on validation set: %s' % valid_loss)
            if lr > 0.0001:
                sess.run(learning_rate_updater)
