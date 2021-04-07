FROM    google/cloud-sdk:alpine

ENV     CLOUDSDK_CORE_DISABLE_PROMPTS 1

# The Pub/Sub emulator requires Java 8
RUN     apk --no-cache add openjdk8-jre

# Install the Pub/Sub emulator, then cleanup the (>400 MB) backup directory
RUN     gcloud config set disable_usage_reporting true && \
        gcloud components install -q \
            beta \
            pubsub-emulator && \
        rm -rf /google-cloud-sdk/.install/.backup

# Create the directory to store Pub/Sub data
RUN     mkdir -p "/tmp/pubsub-data"

# Expose the default emulator port
EXPOSE  8085

ENTRYPOINT ["gcloud", "beta", "emulators", "pubsub", "start", "--data-dir", "/tmp/pubsub-data", "--host-port" ,"0.0.0.0:8085"]
