package net.bondar

/**
 * Script for getting information about closest places on the basic of specified geographical coordinates
 */
args = ["-param","48.4644455,35.0475775,2"]

try{
    def service = new Service()
    def result = service.search(args)
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
