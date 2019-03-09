# Hotelier

## Java web application demonstrating hotel booking

This demo Java web application demonstrates how to manipulate remote endpoints in order to retrieve, transform, present and manipulate data for booking hotel rooms.

## Install and compile

Simply clone the current git repository.

This project utilizes ant to automate the build process. It is currently deployed in Jetty application server so the ant configuration file anticipates the root project folder to be located in {jetty.home}/webapps/ .

You shall end up with  {jetty.home}/webapps/hotelier/ . Issue ant and the project will be build, with the classes being located in WEB-INF/classes.

## Run

From within the project root dir issue ant jetty-start and point your browser to http://localhost:8080/hotelier.
