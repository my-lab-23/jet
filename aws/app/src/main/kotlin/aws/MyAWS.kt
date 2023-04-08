package aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder
import com.amazonaws.services.ec2.model.RunInstancesRequest
import com.amazonaws.services.ec2.model.StopInstancesRequest
import com.amazonaws.services.ec2.model.*
import java.io.File

object MyAWS {

    private val accessKeyId = System.getenv("AWS_ACCESS_KEY_ID")
    private val secretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY")
    private val credentials = BasicAWSCredentials(accessKeyId, secretAccessKey)
    private val credentialsProvider = AWSStaticCredentialsProvider(credentials)

    private val region = Regions.EU_NORTH_1
    private const val amiId = "ami-02c68996dd3d909c1"
    private const val instanceType = "t3.micro"
    private const val keyName = "admin0"
    private const val securityGroupId = "sg-0817266da8cb6e145"

    fun getClient(): AmazonEC2? {

        return AmazonEC2ClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion(region)
            .build()
    }

    fun runInstance(ec2Client: AmazonEC2?, name: String) {

        val tagSpecification = TagSpecification()
            .withResourceType("instance")
            .withTags(Tag("Name", name))

        val runInstancesRequest = RunInstancesRequest()
            .withImageId(amiId)
            .withInstanceType(instanceType)
            .withKeyName(keyName)
            .withSecurityGroupIds(securityGroupId)
            .withMinCount(1)
            .withMaxCount(1)
            .withTagSpecifications(tagSpecification)

        ec2Client?.runInstances(runInstancesRequest)
    }

    fun getRunningInstanceIPs(ec2Client: AmazonEC2?) {

        val ec2 = ec2Client ?: return
        val runningInstances = ec2.describeInstances()
            .reservations
            .flatMap { it.instances }
            .filter { it.state.name == "running" }

        val instanceIPs = runningInstances.mapNotNull { it.publicIpAddress }
        instanceIPs.forEach { println(it) }

        File("./ansible/hosts").writeText("[ec2-instance]\n")

        val instanceDnsName = runningInstances.mapNotNull { it.publicDnsName }
        instanceDnsName.forEach {
            println("ssh -i \"~/.ssh/admin0.pem\" admin@$it")
            File("./ansible/hosts")
                .appendText("$it ansible_user=admin ansible_ssh_private_key_file=~/.ssh/admin0.pem\n")
        }
    }

    fun stopAllRunningInstances(ec2Client: AmazonEC2?) {

        val ec2 = ec2Client ?: return
        ec2.describeInstances().reservations?.forEach { reservation ->
            reservation.instances.forEach { instance ->
                if (instance.state.name == "running") {
                    val request = StopInstancesRequest().withInstanceIds(instance.instanceId)
                    ec2Client.stopInstances(request)
                }
            }
        }
    }

    fun startInstanceByNameTag(ec2Client: AmazonEC2?, nameTag: String) {

        val ec2 = ec2Client ?: return
        val describeInstancesResult = ec2.describeInstances()
        describeInstancesResult.reservations.forEach { reservation ->
            reservation.instances.forEach { instance ->
                instance.tags.forEach { tag ->
                    if (tag.key == "Name" && tag.value == nameTag) {
                        val request = StartInstancesRequest().withInstanceIds(instance.instanceId)
                        ec2.startInstances(request)
                    }
                }
            }
        }
    }
}
