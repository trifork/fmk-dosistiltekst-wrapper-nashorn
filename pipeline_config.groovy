libraries{
    maven
    docker {
        container_dependencies = '[{"name":"dosis2text","image":"registry.fmk.netic.dk/fmk/fmk-dosistiltekst-server:latest"}]'
        java_version = 17
    }
    sonarqube_maven
}
