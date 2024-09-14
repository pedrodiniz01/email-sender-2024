# Use the official Postgres image as the base
FROM postgres:13

# Set environment variables (optional, usually these are set at runtime)
ENV POSTGRES_DB=emailsender
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin

# Copy custom configuration files or initialization scripts here (if needed)
# COPY init.sql /docker-entrypoint-initdb.d/

# Expose the port Postgres runs on
EXPOSE 5432
