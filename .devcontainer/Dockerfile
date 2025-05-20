FROM mcr.microsoft.com/devcontainers/java:17

# Install Fluxbox and noVNC
RUN apt-get update && apt-get install -y \
    fluxbox \
    novnc \
    websockify \
    x11vnc \
    xterm && \
    apt-get clean

# Set up noVNC
RUN mkdir -p /opt/novnc && \
    ln -s /usr/share/novnc/vnc.html /opt/novnc/index.html

# Expose VNC and noVNC ports
EXPOSE 5900 6080

# Start Fluxbox and noVNC
CMD ["sh", "-c", "fluxbox & websockify --web=/usr/share/novnc/ 6080 localhost:5900"]