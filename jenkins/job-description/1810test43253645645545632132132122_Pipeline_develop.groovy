def scripts = """

def job_config = [
        job: [
                name: "1810test43253645645545632132132122_Pipeline_develop",
                agent: "maven"
        ],
        git: [
                branch: "develop"
        ]
]

def config = [
        git: [
                protocol: "https",
                server: "github.developer.allianz.io",
                credentialsId: "git-token-credentials"
             ]
           ]

def lib = library identifier: 'BizDevOps_JSL@develop', retriever: modernSCM(
  [\$class: 'GitSCMSource',
   remote: 'https://github.developer.allianz.io/JEQP/BizDevOps-JSL.git',
   credentialsId: 'git-token-credentials']) 

def customLib = library identifier: '1810test43253645645545632132132122_JSL@develop', retriever: modernSCM(
  [\$class: 'GitSCMSource',
   remote: 'https://github.developer.allianz.io/jarro06/1810test43253645645545632132132122_lib.git',
   credentialsId: 'git-token-credentials']) 
   
def jslGeneral    = lib.de.allianz.bdo.pipeline.JSLGeneral.new()
def jslGit        = lib.de.allianz.bdo.pipeline.JSLGit.new()
def jslGhe        = lib.de.allianz.bdo.pipeline.JSLGhe.new()

def jslCustom     = customLib.de.allianz.1810test43253645645545632132132122.new()

def manual_commit_sha

// for questions about this job ask mario akermann/tobias pfeifer from team pipeline

pipeline {
    agent { label job_config.job.agent }
    stages {
        stage('Prepare') {
            steps {
                echo "prepare"
                script {
                   jslGeneral.clean()
                }
            }    
        }
        stage('Checkout') {
            steps {
                echo "checkout"
                script {
                   jslGit.checkout( config, "jarro06", "1810test43253645645545632132132122", job_config.git.branch)
               }
            }    
        }
        stage('Build') {
            steps {
                echo "Build"
                script {
                      jslCustom.build()                  
                }
            }    
        }
        stage('Component Tests') {
            steps {
                echo "Component Tests"
                script {
                    jslCustom.componentTest()
                }
            }    
        }
        stage('Integration Tests') {
            steps {
                echo "Integration Tests"
                script {
                     jslCustom.integrationTest()
                }
            }    
        }
        stage('UAT Tests') {
            steps {
                echo "UAT Tests"
                script {
                        jslCustom.uatTest()
                }
            }    
        }
        stage('Acceptance Tests') {
            steps {
                echo "Acceptance Tests"
                script {
                        jslCustom.acceptanceTest()
                    }
                }
            }    
        stage('Publish Artifacts') {
            steps {
                echo "Publish Artifacts"
                script {
                        jslCustom.publishArtifacts()
                    }
                }
            }    
        stage('Publish Results') {
            steps {
                echo "Publish Results"
                script {
                        junit allowEmptyResults: true, testResults: '**/surefire-reports/TEST-*.xml'
                    }
                }
            }    
        }
    }
    
"""
        
def job = pipelineJob("1810test43253645645545632132132122_Pipeline_develop");

job.with {

    authenticationToken('reKItNevxiHI9hwJwhWuBecn6fIHciHq')
    
    definition {
        cps {
            script(scripts)
                sandbox()
        }
    }
}  
