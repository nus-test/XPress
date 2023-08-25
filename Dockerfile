#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /experiment
COPY . ./XPress
RUN git clone https://github.com/BaseXdb/basex.git /basex
RUN mvn --global-settings ./XPress/scripts/settings.xml -f ./XPress/pom.xml install -DskipTests
RUN chmod +x ./XPress/scripts/run_evaluation.sh
EXPOSE 9000
WORKDIR '/experiment/XPress'
ENTRYPOINT ["./scripts/run_evaluation.sh"]