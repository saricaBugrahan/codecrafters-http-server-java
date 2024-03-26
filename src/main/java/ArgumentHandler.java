public class ArgumentHandler {

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
            }
        }
    }
}
