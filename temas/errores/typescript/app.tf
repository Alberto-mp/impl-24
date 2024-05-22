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

resource "docker_image" "almacen_app" {
  name = "almacen_app"
  keep_locally = true
}

resource "docker_container" "almacen_app" {
  name  = "almacen_app_container"
  image = "almacen_app"
  ports {
    internal = 3300
    external = 3300
  }
}