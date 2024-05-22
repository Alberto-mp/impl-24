# Etapa 1: Construcción del entorno
FROM dart:stable AS build

# Copiar los archivos de la aplicación
WORKDIR /app
COPY pubspec.* ./
COPY lib ./lib
COPY web ./web
COPY analysis_options.yaml ./

# Obtiene las dependencias de la aplicación
RUN dart pub get

# Construye la aplicación web
RUN dart pub global activate webdev
RUN dart pub global run webdev build --output web:build

# Etapa 2: Crear el contenedor de producción
FROM nginx:alpine

# Copia los archivos construidos a la imagen de Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Expone el puerto 80
EXPOSE 80

# Comando para ejecutar Nginx
CMD ["nginx", "-g", "daemon off;"]
