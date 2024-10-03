# Use the official PostgreSQL image for linux/arm64/v8 platform
FROM postgres

# Set environment variables for PostgreSQL
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=emailsender

# Expose the default PostgreSQL port
EXPOSE 5432

# Copy any custom initialization scripts (optional, e.g., to initialize the database with tables)
# COPY ./init.sql /docker-entrypoint-initdb.d/

# Command to run PostgreSQL
CMD ["postgres"]
