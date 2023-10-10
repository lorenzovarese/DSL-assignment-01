FROM mcr.microsoft.com/devcontainers/base:jammy

RUN apt update
RUN apt -y install --no-install-recommends graphviz

USER vscode

RUN curl -s "https://get.sdkman.io" | bash
RUN chmod +x "$HOME/.sdkman/bin/sdkman-init.sh"
RUN "$HOME/.sdkman/bin/sdkman-init.sh"

RUN zsh -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21-open"
RUN zsh -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install scala 3.3.1"
RUN zsh -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install sbt"

