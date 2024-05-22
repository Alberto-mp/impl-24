FROM ubuntu:latest

# Instalar dependencias
RUN apt-get update && apt-get install -y \
   build-essential \
   curl \
   git \
   sudo \
   ruby-full \
   && rm -rf /var/lib/apt/lists/*

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el Gemfile del proyecto
COPY Gemfile /app/

# Instalar las gemas especificadas en el Gemfile
RUN gem install bundler && bundle install

# Copiar el resto de los archivos del proyecto
COPY app.rb /app/app.rb
COPY src /app/src

# Configurar el comando para iniciar la aplicación
CMD ["ruby", "app.rb", "-o", "0.0.0.0"]