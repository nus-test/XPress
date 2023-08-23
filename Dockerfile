#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /experiment
COPY ../XPress ./XPress
COPY evaluation_config.txt ./evaluation_config.txt
RUN git clone https://github.com/BaseXdb/basex.git /basex
RUN mvn -f ./XPress/pom.xml install -DskipTests
EXPOSE 9000
WORKDIR '/experiment/XPress'
ENTRYPOINT ["/run_evaluation.sh"]