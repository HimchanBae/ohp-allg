FROM icr.io/appcafe/open-liberty:full-java11-openj9-ubi
USER root
WORKDIR /
RUN mkdir -p /home/wallet

COPY --chown=1001:0 src/main/resources/appUser.id /home/wallet/appUser.id
COPY --chown=1001:0 src/main/liberty/config/ /config/
COPY --chown=1001:0 target/*.war /config/apps/

RUN configure.sh

EXPOSE 9080