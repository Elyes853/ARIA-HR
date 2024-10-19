# Use Alpine-based Nginx image
FROM nginx:alpine

# Install Bash
RUN apk update && apk add bash

# Copy Nginx configuration
COPY src/nginx/etc/conf.d/default.conf /etc/nginx/conf.d/default.conf

# Copy built Angular app from the builder stage
COPY --from=builder /app/dist/ariarh-webapp /usr/share/nginx/html/

# Change directory ownership to the user "nginx"
RUN chown -R nginx:nginx /usr/share/nginx/html/

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
