terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.1"
    }
  }
}

provider "docker" {}

resource "docker_container" "miapp_rubydel" {
  # Crea un contenedor basado en la imagen Docker construida
  name  = "miapp_rubydel"
  image = "delegacion_img"
  ports {
    internal = 4567
    external = 4567
  }
}