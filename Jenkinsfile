
properties([
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10')),
        githubProjectProperty(displayName: '', projectUrlStr: 'https://github.com/trifork/fmk-dosistiltekst-wrapper-nashorn/'),
        pipelineTriggers([pollSCM('H/5 * * * * ')]),
        parameters([
                booleanParam(
                        name: 'MAKE_RELEASE',
                        defaultValue: false,
                        description: 'Set this if you want to build a release. Otherwise a snapshot will be build'
                ),
                booleanParam(
                        name: 'skipUnitTests',
                        defaultValue: false,
                        description: 'skip sonarqube analysis'
                ),
                booleanParam(
                        name: 'skipSonarQube',
                        defaultValue: false,
                        description: 'skip sonarqube analysis'
                )
            ])
])
pipeline {
    agent { label 'local' }
    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                checkout([$class: 'GitSCM', branches: [[name: "*/master"]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'fmk-dosistiltekst-wrapper-nashorn']], submoduleCfg: [], userRemoteConfigs: [[url: 'git@github.com:trifork/fmk-dosistiltekst-wrapper-nashorn.git']]])
            }
        }
        stage('Build') {
            steps {
                script {
                    String jenkinsUserId = sh(returnStdout: true, script: 'id -u jenkins').trim()
                    String dockerGroupId = sh(returnStdout: true, script: 'getent group docker | cut -d: -f3').trim()
                    String containerUserMapping = "-u $jenkinsUserId:$dockerGroupId "
                    docker.image("registry.fmk.netic.dk/fmk/fmkbuilder:11").inside(containerUserMapping + "--add-host archive.ubuntu.com:2001:67c:1562::15 --add-host test1-ecpr2.fmk.netic.dk:2a03:dc80:0:f12d::170 --add-host f.aia.systemtest19.trust2408.com:2a03:dc80:0:f12d::120 --add-host nodejs.org:2400:cb00:2048:1::6814:172e --add-host registry.bower.io:2400:cb00:2048:1::6818:69ac --add-host registry.npmjs.org:2606:4700::6810:1923 --add-host f.aia.ica02.trust2408.com:2a03:dc80:0:f12d::120 -e _JAVA_OPTIONS='-Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=false -Djava.net.preferIPv6Stack=true -Djava.net.preferIPv6Addresses=true' -v $HOME/.m2:/home/jenkins/.m2") {
                        configFileProvider([configFile(fileId: 'trifork-ci-fmk-settings', variable: 'MAVEN_SETTINGS')]) {
                            env.version = sh script: "cd fmk-dosistiltekst-wrapper-nashorn && mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true
                            env.version = env.version.trim()
                            if (MAKE_RELEASE != 'true') {
                                env.version = env.version + "-SNAPSHOT"
                            }
                            // ensures the published pom in nexus contains correct version. Gradle don't like mismatches
                            sh "cd fmk-dosistiltekst-wrapper-nashorn && sed -i 's/\${revision}/${env.version}/' pom.xml"
                            currentBuild.displayName = "#${BUILD_NUMBER} ${env.version}"
                            try {
                                sh "cd fmk-dosistiltekst-wrapper-nashorn &&mvn -s $MAVEN_SETTINGS deploy -Drevision=${env.version} -DskipTests=${env.skipUnitTests}"
                            } finally {
                                try {
                                    junit '**/target/surefire-reports/**/TEST-*.xml'
                                } catch (ignored) {
                                    //ignored
                                }
                            }
                        }
                    }
                }
            }
        }
        stage('sonarqube') {
            when { equals expected: true, actual: !env.skipUnitTests.toBoolean() && !env.skipSonarQube.toBoolean() }
            steps {
                script {
                    String jenkinsUserId = sh(returnStdout: true, script: 'id -u jenkins').trim()
                    String dockerGroupId = sh(returnStdout: true, script: 'getent group docker | cut -d: -f3').trim()
                    String containerUserMapping = "-u $jenkinsUserId:$dockerGroupId "
                    docker.image("registry.fmk.netic.dk/fmk/fmkbuilder:11").inside(containerUserMapping + "--add-host archive.ubuntu.com:2001:67c:1562::15 --add-host test1-ecpr2.fmk.netic.dk:2a03:dc80:0:f12d::170 --add-host f.aia.systemtest19.trust2408.com:2a03:dc80:0:f12d::120 --add-host nodejs.org:2400:cb00:2048:1::6814:172e --add-host registry.bower.io:2400:cb00:2048:1::6818:69ac --add-host registry.npmjs.org:2606:4700::6810:1923 --add-host f.aia.ica02.trust2408.com:2a03:dc80:0:f12d::120 -e _JAVA_OPTIONS='-Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=false -Djava.net.preferIPv6Stack=true -Djava.net.preferIPv6Addresses=true' -v $HOME/.m2:/home/jenkins/.m2") {
                        configFileProvider([configFile(fileId: 'trifork-ci-fmk-settings', variable: 'MAVEN_SETTINGS')]) {
                            withSonarQubeEnv('SonarQube') {
                                sh "cd fmk-dosistiltekst-wrapper-nashorn && mvn -s $MAVEN_SETTINGS sonar:sonar -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml"
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        failure {
            emailext body: '$DEFAULT_CONTENT',
                    recipientProviders: [culprits(), requestor()],
                    subject: '$DEFAULT_SUBJECT'
        }
        unstable {
            emailext body: '$DEFAULT_CONTENT',
                    recipientProviders: [culprits(), requestor()],
                    subject: '$DEFAULT_SUBJECT'
        }
        fixed {
            emailext body: '$DEFAULT_CONTENT',
                    recipientProviders: [culprits(), requestor()],
                    subject: '$DEFAULT_SUBJECT'
        }
    }
}
