package net.bondar

/**
 * Script for getting information about closest places on the specified geographical coordinates
 */
args = ["48.4644455", "35.0475775", "2"]

try{
    def service = new Service()
    def result = service.search(args[0], args[1], args[2])
    println("\nOperation Completed")

}
catch (Exception e){
    println(e.message)
    println("Operation failed")
    return
}
catch (Throwable t){
    t.printStackTrace();
    println("Unexpected error: ${t.getMessage()}");
}
