terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.1"
    }
  }
}

provider "docker" {}

resource "docker_container" "miapp_rubyabs" {
  # Crea un contenedor basado en la imagen Docker construida
  name  = "miapp_rubyabs"
  image = "abstraccion_img"
  ports {
    internal = 4567
    external = 4567
  }
}