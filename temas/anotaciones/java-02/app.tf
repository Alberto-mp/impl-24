terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.1"
    }
  }
}

provider "docker" {
  host = "npipe:////.//pipe//docker_engine"
}

resource "docker_image" "logger_app" {
  name = "logger_app"
  keep_locally = true
}

resource "docker_container" "logger_app" {
  name  = "logger_app_container"
  image = "logger_app"
  ports {
    internal = 6060
    external = 6060
  }
}