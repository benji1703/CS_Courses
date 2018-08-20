from __future__ import print_function
# Import MNIST data
# from tensorflow.examples.tutorials.mnist import input_data
import tensorflow as tf
import os
model_path = "../out/"
print("Loading data...")
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("../res/MNIST_data/", one_hot=True)
print("Data loaded successfully.")
# Do not modify the above


def build_out_path():
    '''
    Do not modify
    '''
    if chosen_network_architecture == my_vanilla_neural_net:
        out_path = model_path + 'my_vanilla/'
    elif chosen_network_architecture == conv_net:
        out_path = model_path + 'conv/'
    else:
        raise Warning('You must use the original method names. Restore my_vanilla and conv_net.')
    return out_path


def remove_old_models():
    '''
    Do not modify
    '''
    out_path = build_out_path()
    filelist = [ f for f in os.listdir(out_path)]
    for f in filelist:
        os.remove(os.path.join(out_path, f))


def conv_net(x):

    # MNIST data input is a 1-D vector of 784 features (28*28 pixels)
    # Reshape to match picture format [Height x Width x Channel]
    # Tensor input becomes 4-D: [Batch Size, Height, Width, Channel]
    x = tf.reshape(x, shape=[-1, 28, 28, 1])

    ### YOUR CODE STARTS HERE ###

    # Convolution Layer with F1 filters, a kernel size of K1 and ReLU activations
    F1 = 32
    K1 = 5
    S1 = 2
    K2 = 2
    N = 256

    conv1 = tf.layers.conv2d(x, F1, K1, activation=tf.nn.relu)
    # Max Pooling (down-sampling) with strides of S1 and kernel size of K2
    pool1 = tf.layers.max_pooling2d(conv1, S1, K2)

    # Flatten the data to a 1-D vector for the fully connected layer
    pool2_flat = tf.contrib.layers.flatten(pool1)

    # Fully connected layer of width N
    fc1 = tf.layers.dense(pool2_flat, N)

    # Output layer, class prediction
    out = tf.layers.dense(fc1, n_classes)

    ### YOUR CODE ENDS HERE ###

    return out


def my_vanilla_neural_net(x):
    '''
    Creating a feed-forward fully-connected neural network
    '''
    # First fully connected layer of size N1
    N1 = 784
    fc1 = tf.layers.dense(x, N1, activation=tf.nn.relu)
    # Second fully connected layer of size N2
    N2 = 784
    fc2 = tf.layers.dense(fc1, N2, activation=tf.nn.relu)
    # Output layer, class prediction
    out = tf.layers.dense(fc2, n_classes)
    return out


def train_test_model(save_final_model=False):
    # Running the training session
    print("Starting training session...")
    with tf.Session() as sess:

        # Run the initializer
        sess.run(init)

        # Training cycle
        for epoch in range(n_epochs):
            avg_cost = 0.
            total_batch = int(mnist.train.num_examples/batch_size)
            # Loop over all batches
            for i in range(total_batch):
                batch_x, batch_y = mnist.train.next_batch(batch_size)
                # Run optimization op (backprop) and cost op (to get loss value)
                _, c = sess.run([optimizer, cost], feed_dict={x: batch_x,
                                                              y: batch_y})
                # Compute average loss
                avg_cost += c / total_batch
            # Display logs per epoch step
            if epoch % display_step == 0:
                # Test model
                correct_prediction = tf.equal(tf.argmax(pred, 1), tf.argmax(y, 1))
                # Calculate accuracy
                accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
                train_err = 1-accuracy.eval({x: mnist.train.images, y: mnist.train.labels})
                valid_err = 1-accuracy.eval({x: mnist.validation.images, y: mnist.validation.labels})
                print("Epoch:", '%05d' % (epoch + 1), ", cost=", \
                      "{:.9f}".format(avg_cost), ", train_err=", "{:.4f}".format(train_err), ", valid_err=",
                      "{:.4f}".format(valid_err))
        print("\nOptimization Finished!\n")

        # Test model
        correct_prediction = tf.equal(tf.argmax(pred, 1), tf.argmax(y, 1))
        # Calculate accuracy
        accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
        train_err = 1 - accuracy.eval({x: mnist.train.images, y: mnist.train.labels})
        valid_err = 1 - accuracy.eval({x: mnist.validation.images, y: mnist.validation.labels})
        print("Optimized for ", '%05d' % (epoch + 1), "epochs, to obtain training error", "{:.4f}".format(train_err),
              ", and validation error", "{:.4f}".format(valid_err))
        confusion = tf.confusion_matrix(tf.argmax(pred, 1), tf.argmax(y, 1))
        print("\nValidation Confusion matrix:\n",
              confusion.eval({x: mnist.validation.images, y: mnist.validation.labels}))

        if save_final_model:
            out_path = build_out_path()
            remove_old_models()
            # Save model weights to disk
            save_path = saver.save(sess, out_path)
            print("Model saved in file: %s" % out_path)


def restore_test_model():
    # Restoring model - you must make sure you're seeing the expected results
    print("Starting test session...")
    with tf.Session() as sess:
        # Initialize variables
        sess.run(init)

        # Restore model weights from previously saved model
        out_path = build_out_path()
        try:
            saver.restore(sess, out_path)
            print("Model restored from file: %s" % out_path)
        except:
            raise Warning("Did you save the model?")

        # Test model
        correct_prediction = tf.equal(tf.argmax(pred, 1), tf.argmax(y, 1))
        # Calculate accuracy
        accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
        train_err = 1 - accuracy.eval({x: mnist.train.images, y: mnist.train.labels})
        valid_err = 1 - accuracy.eval({x: mnist.validation.images, y: mnist.validation.labels})
        print("Using saved model. Train_err=", "{:.4f}".format(train_err), ", valid_err=",
              "{:.4f}".format(valid_err))




if __name__ == "__main__":

    # Network Parameters
    n_input = 784  # MNIST data input (img shape: 28*28)
    n_classes = 10  # MNIST total classes (0-9 digits)

    # Parameters
    learning_rate = 0.0007
    batch_size = 400
    n_epochs = 30
    display_step = 1

    # tf Graph input
    x = tf.placeholder("float", [None, n_input])
    y = tf.placeholder("float", [None, n_classes])

    """
    # Choose which network you're training by commenting out the one you're not:
    """
    # chosen_network_architecture = my_vanilla_neural_net  # You may comment out as desired. DO NOT modify the name of this method.
    chosen_network_architecture = conv_net  # You may comment out as desired. DO NOT modify the name of this method.

    ### DO NOT MODIFY CODE BETWEEN THESE LINES - START ###
    pred = chosen_network_architecture(x)

    # Define loss and optimizer
    cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits_v2(logits=pred, labels=y))
    optimizer = tf.train.AdamOptimizer(learning_rate=learning_rate).minimize(cost)

    # Initialize the variables (i.e. assign their default value)
    init = tf.global_variables_initializer()
    # 'Saver' op to save and restore all the variables
    saver = tf.train.Saver()
    ### DO NOT MODIFY CODE BETWEEN THESE LINES - END ###


    # TRAINING YOUR MODEL
    """
    # Turn save_final_model to True when you're ready to save your final model (i.e. saving its trained state). (!) Make sure you can restore and test.
    """
    # train_test_model(save_final_model=True)
    restore_test_model() # use this only at the end to verify your model has been properly saved - failure to submit a saved model will receive a grade of 0.
