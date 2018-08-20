/*
 * ex2.c
 * Benjamin Arbibe
 * 323795633
 */
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <curl/curl.h>

#define HTTP_OK 200L
#define REQUEST_TIMEOUT_SECONDS 2L

#define URL_OK 0
#define URL_ERROR 1
#define URL_UNKNOWN 2

#define MAX_PROCESSES 1024

typedef struct {
    int ok, error, unknown;
} UrlStatus;


UrlStatus countChildrenToParent(UrlStatus *resultChild, UrlStatus *resultPar);

void resultParPrinter(UrlStatus *resultPar);

void usage() {
    fprintf(stderr, "usage:\n\t./ex2 FILENAME NUMBER_OF_PROCESSES\n");
    exit(EXIT_FAILURE);
}

int check_url(const char *url) {
    CURL *curl;
    CURLcode res;
    long response_code = 0L;

    curl = curl_easy_init();

    if(curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url);
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
        curl_easy_setopt(curl, CURLOPT_TIMEOUT, REQUEST_TIMEOUT_SECONDS);
        curl_easy_setopt(curl, CURLOPT_NOBODY, 1L); /* do a HEAD request */

        res = curl_easy_perform(curl);
        if(res == CURLE_OK) {
            curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);
            if (response_code == HTTP_OK) {
                return URL_OK;
            } else {
                return URL_ERROR;
            }
        }

        curl_easy_cleanup(curl);
    }
    return URL_UNKNOWN;
}

void serial_checker(const char *filename) {
    UrlStatus results = {0};
    FILE *toplist_file;
    char *line = NULL;
    size_t len = 0;
    ssize_t read;

    toplist_file = fopen(filename, "r");

    if (toplist_file == NULL) {
        exit(EXIT_FAILURE);
    }

    while ((read = getline(&line, &len, toplist_file)) != -1) {
        if (read == -1) {
            perror("unable to read line from file");
        }
        line[read-1] = '\0'; /* null-terminate the URL */
        switch (check_url(line)) {
            case URL_OK:
                results.ok += 1;
                break;
            case URL_ERROR:
                results.error += 1;
                break;
            default:
                results.unknown += 1;
        }
    }

    free(line);

    // Small change from given code - for safety
    if(fclose(toplist_file) == -1){
        perror("error when closing the file");
        exit(EXIT_FAILURE);
    }

    printf("%d OK, %d Error, %d Unknown\n",
           results.ok,
           results.error,
           results.unknown);
}

void worker_checker(const char *filename, int pipe_write_fd, int worker_id, int workers_number) {
    UrlStatus results = {0};
    FILE *toplist_file;
    char *line = NULL;
    size_t len = 0;
    ssize_t read;
    int count = 0;
    toplist_file = fopen(filename, "r");
    if (toplist_file == NULL) {
        exit(EXIT_FAILURE);
    }
    while ((read = getline(&line, &len, toplist_file)) != -1) {
        if (read == -1) {
            perror("unable to read line from file");
        }
        line[read-1] = '\0'; /* null-terminate the URL */
        if(count % workers_number == worker_id){ // Using the HINT
               switch (check_url(line)) {
           case URL_OK:
               results.ok += 1;
               break;
           case URL_ERROR:
               results.error += 1;
               break;
           default:
               results.unknown += 1;
        }
    }

        count++;
    }

    free(line);
    
    if(fclose(toplist_file) == -1){
        perror("error closing the file");
        exit(EXIT_FAILURE);

    }
    if(write(pipe_write_fd, &results,  sizeof(UrlStatus)) == -1){
        perror("error writing to the pipe");
        exit(EXIT_FAILURE);
    }
    if(close(pipe_write_fd) == -1){
        perror("error closing the pipe");
        exit(EXIT_FAILURE);
    }
}




void parallel_checker(const char *filename, int number_of_processes) {

    pid_t pid;
    UrlStatus resultChild = {0};
    UrlStatus resultPar = {0};
    int arrPipe[MAX_PROCESSES * 2]; // Using the given MAX_PROCESSES defined in the code

    for (int i = 0; i < number_of_processes; i++){
        if(pipe(&arrPipe[i * 2]) == -1){
            perror("error opening pipe");
            exit(EXIT_FAILURE);
        }
        pid = fork();
        if(pid == -1){
            perror("error forking");
            exit(EXIT_FAILURE);
        }
        if(pid == 0){
            if(close(arrPipe[i * 2]) == -1){
                perror("error closing reading pipe");
                exit(EXIT_FAILURE);
            }
            worker_checker(filename, arrPipe[(i * 2) + 1], i, number_of_processes);
            exit(EXIT_SUCCESS);
        }
    }
    // Waiting to the children to finish
    for (int i = 0; i < number_of_processes; i++) {
        if(wait(NULL) == -1){
            perror("error waiting");
            exit(EXIT_FAILURE);
        }
    }
    // Safety and counting section ->
    for (int i = 0; i < number_of_processes; i++){
        // -> CLOSING the WRITING PART of pipe
        if(close(arrPipe[(i * 2) + 1]) == -1){
            perror("error closing writing pipe");
            exit(EXIT_FAILURE);
        }
        // -> READING from the pipe
        if(read(arrPipe[i * 2], &resultChild, sizeof(UrlStatus)) == -1){
            perror("error reading pipe");
            exit(EXIT_FAILURE);
        }
        // -> COUNTING
        resultPar = countChildrenToParent(&resultChild, &resultPar);
        // -> CLOSING the READING PART of pipe
        if(close(arrPipe[i * 2]) == -1){
            perror("error closing reading pipe");
            exit(EXIT_FAILURE);
        }
    }
    // -> Printing
    resultParPrinter(&resultPar);
}

UrlStatus countChildrenToParent(UrlStatus *resultChild, UrlStatus *resultPar) {
    (*resultPar).ok += (*resultChild).ok;
    (*resultPar).error += (*resultChild).error;
    (*resultPar).unknown += (*resultChild).unknown;
    return (*resultPar);
}

void resultParPrinter(UrlStatus *resultPar) {
    printf("%d OK , %d Error, %d Unknown\n", (*resultPar).ok, (*resultPar).error, (*resultPar).unknown);
}

int main(int argc, char **argv) {
    if (argc != 3) {
        usage();
    } else if (atoi(argv[2]) == 1) {
        serial_checker(argv[1]);
    } else {
        parallel_checker(argv[1], atoi(argv[2]));
    }
    return EXIT_SUCCESS;
}
