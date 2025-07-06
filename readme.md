# Java Job Board

This repository serves as a demonstration project for building a basic job board application using Java Spring Boot, HTMX and Tailwind.

## Purpose

I'm using this repository to learn Java Spring Boot and practice building RESTful applications.

## Run

### Prerequisite

1. `terraform plan`
1. `terraform apply -var-file="terraform.tfvars"`

### Local development

1. `docker-compose up -d db cache`
1. add `"envFile": "${workspaceFolder}/.env"` to `.csvode/launch.json`
1. run java from your IDE (in debug mode)

### Docker

1. `export $(cat .env | xargs) && ./mvnw package`
1. `docker-compose up --build`

## Features (all in WIP)

- Login via Microsoft SSO
- Create Companys and Jobs
- Apply to jobs
- Candidate (user) and job poster (admin) dashboard
- Store data in PostgreSQL
- Cache requests in Redis

## TODOs

1. Finish building features
1. Clean up Controllers that reference multiple services (use facades)
1. Error handling
1. Tests
1. Modularize views
1. ...
