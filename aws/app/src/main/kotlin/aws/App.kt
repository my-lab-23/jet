package aws

fun main(argv: Array<String>) {

    val awsClient = MyAWS.getClient()

    when(argv[0]) {

        "0" -> MyAWS.runInstance(awsClient, "My")
        "1" -> MyAWS.getRunningInstanceIPs(awsClient)
        "2" -> MyAWS.stopAllRunningInstances(awsClient)
        "3" -> MyAWS.startInstanceByNameTag(awsClient, "My")
        "h" -> {

            println("0 - Create instance My.")
            println("1 - Get address.")
            println("2 - Stop all.")
            println("3 - Start My.")
        }
    }
}
