# Método para Pago de Usuarios

## Requisitos
- **IntelliJ IDEA**
- **Docker**

## Pasos para Migrar la Base de Datos

1. **Abrir una instancia de PowerShell**  
   - Navega hasta la carpeta **PayMeth** donde se encuentra el archivo `paymentDB.sql`.
![image](https://github.com/user-attachments/assets/43d7c2ff-c602-4c56-85e5-0933aa218c13)

2. **Crear un nuevo contenedor de PostgreSQL**  
   Ejecuta el siguiente comando para iniciar el contenedor:  
   ```bash
   docker run -d --name postgreSQL -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=paymentDB -p 5432:5432 -v ${PWD}/db-data:/var/lib/postgresql/data postgres:latest

3. **Copiar el archivo `paymentDB.sql` al contenedor**  
   Ejecuta el siguiente comando:  
   ```bash
   docker cp ${PWD}/paymentDB.sql postgreSQL:/paymentDB.sql

Este comando crea un contenedor PostgreSQL llamado postgreSQL y asigna persistencia de datos mediante un volumen.

**Nota: **
En caso de existir la carpetaa db-data eliminarla antes dee hacer la importacion de la base de datos

4. **Migrar la base de datos al contenedor**
   Ejecuta el siguiente comando :  
   ```bash
   Get-Content ${PWD}\paymentDB.sql | docker exec -i postgreSQL psql -U postgres -d paymentDB
Este comando migra el contenido del archivo `paymentDB.sql` a la base de datos paymentDB dentro del contenedor.
