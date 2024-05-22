terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.1"
    }
  }
}

provider "docker" {}

resource "docker_container" "miapp_dart" {
  # Crea un contenedor basado en la imagen Docker construida
  name = "miapp_dart"
  image = "herencia_img"
  ports {
    internal = 80
    external = 8080
  }
}