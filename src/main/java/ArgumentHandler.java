public class ArgumentHandler {

    private int serverPortNumber = 4221;

    public ArgumentHandler(String[] args){
        handleArgs(args);
    }

    private void handleArgs(String[] args){
        if (args.length < 1){
            System.out.println("Started with zero input");
        }
        else{
            for(int i = 0;i<args.length;i++){
                if(args[i].equalsIgnoreCase("--directory")){
                    FileHandler.setFolderPath(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--port")){
                    serverPortNumber = Integer.parseInt(args[++i]);
                }
            }
        }
    }
    public int getServerPortNumber(){
        return serverPortNumber;
    }
}
