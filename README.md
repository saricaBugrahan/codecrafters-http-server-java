# HTTP Server from Scratch

## Overview

This is a custom HTTP server implementation written in Java. It provides a basic framework for handling HTTP requests and serving static files.

## Features

- **HTTP/1.1 Compatible:** Supports HTTP/1.1 protocol for communication with clients.
- **Static File Serving:** Capable of serving static files (HTML, CSS, JavaScript, etc.) to clients.
- **Multithreading:** Handles multiple client requests concurrently using threading.
- **Customizable Configuration:** Allows specifying the directory from which to serve files.
- **Error Handling:** Provides appropriate HTTP responses for various scenarios, like file not found or unsupported HTTP methods.

## Components

### ArgumentHandler

- Parses command-line arguments to configure server settings, such as the directory from which to serve files.

### ClientHandler

- Manages communication with individual clients, handling incoming HTTP requests and generating appropriate responses.

### FileHandler

- Handles file operations, such as searching for files in the specified directory and writing data to files.

### HTTPDecoder

- Parses HTTP request messages and extracts relevant information, such as HTTP method, path, and headers.

### HTTPEncoder

- Generates HTTP response headers based on the server's response to client requests.

### Main

- The entry point of the server application, responsible for initializing the server socket and handling incoming client connections.

## Usage

1. Compile the source files: `javac *.java`
2. Run the server: `java Main`
3. Access the server from a web browser or send HTTP requests programmatically.

## Configuration

- Use the `--directory` option to specify the directory from which to serve files.

Example:
```bash
java Main --directory /path/to/files
