import java.util.Scanner;

public class Main{
    public static Scanner LER = new Scanner(System.in);

    public static void main(String[] args){
        
        String ip = lerIp();
        int mascara = lerMascara();

        String ipBinario = converterParaBinario(ip);
        System.out.printf("\nEndereço IP (binário) : %s\n",ipBinario);
        System.out.printf("Máscara (prefixo)     : /%d\n", mascara);
        String mascaraBinario = converterMascaraBinario(mascara);
        System.out.printf("Máscara (binario)     : %s\n", mascaraBinario);
        String mascaraDecimal = converterParaDecimal(mascaraBinario);
        System.out.printf("Máscara (decimal)     : %s\n", mascaraDecimal);
        String redeBinario = calcularRedeBinario(mascaraBinario, ipBinario);
        String redeDecimal = converterParaDecimal(redeBinario);
        System.out.printf("Endereço da rede      : %s\n", redeDecimal);
        String broadcastBinario = calcularBroadcastBinario(mascaraBinario, redeBinario);
        String broadcastDecimal = converterParaDecimal(broadcastBinario);
        System.out.printf("Endereço da rede      : %s\n", broadcastDecimal);
        double totalHost = calcularQuantidadeDeHosts(mascara);
        System.out.printf("Quantidade de hosts   : %.0f", totalHost);
        String[] faixaHost = verFaixa(redeDecimal, broadcastDecimal);
        System.out.printf("Faixa de hosts        : %s a %s", faixaHost[0], faixaHost[1]);
 


        
    }


    public static String lerIp(){
        System.out.printf("Digite o IP (X.X.X.X): ");
        String ip = LER.next().trim();
        String[] x = ip.split("\\.");

        if(x.length == 4){
            return ip;
        }else{
            System.out.println("IP inválido! Digite novamente.");
            return lerIp();
        }

    }

    public static int lerMascara(){
        System.out.printf("Digite a máscara (/XX): ");
        String mascara = LER.next().trim();
        
        if(mascara.startsWith("/")){
            String y = mascara.substring(1);
            if(y.matches("\\d{1,2}")){
                int num = Integer.parseInt(y);
                if(num >=1 && num<=32){
                    return num;
                }
            }
        }
        System.out.printf("Máscara inválida! Digite novamente.");
        return lerMascara();
        

    }

    public static String converterParaBinario(String ip){
    String[] numeros = ip.split("\\.");
    String binario = "";
    for (int i = 0; i < numeros.length; i++) {
        String z = String.format("%8s", Integer.toBinaryString(Integer.parseInt(numeros[i]))).replace(' ', '0');
        if(i == numeros.length - 1){
            binario+=z;
        }else{
            binario+=z+".";
        }
    }

    return binario;


        
    }

    public static String converterMascaraBinario(int mascara){
        String mascaraBinario = "";
        for (int i = 0; i < 35; i++) {
            if(i == 8 || i == 17 || i == 26){
                mascaraBinario+=".";
            }else if(i < mascara+2){
                mascaraBinario+="1";
            }else{
                mascaraBinario+="0";
            }
        }
        return mascaraBinario;
        
        
    }

    public static String converterParaDecimal(String binario){
        String decimal = "";
        String[] x = binario.split("\\.");
        for (int i = 0; i < x.length; i++) {
            if(i != x.length - 1){
                decimal+=Integer.parseInt(x[i], 2)+".";
            }else{
                decimal+=Integer.parseInt(x[i], 2);
            }
        }
        return decimal;
        
        
    }

    public static String calcularRedeBinario(String mascaraBinario, String ipBinario){
        String redeBinario = "";

        for (int i = 0; i < 35; i++) {
            if(mascaraBinario.charAt(i) == '.'){
                redeBinario+=".";
            }else if(mascaraBinario.charAt(i) == ipBinario.charAt(i) && mascaraBinario.charAt(i) == '1'){
                redeBinario+=1;
            }else{
                redeBinario+=0;
            }
        }

        return redeBinario;
    }

    public static String calcularBroadcastBinario(String mascaraBinario, String redeBinario){
        String broadcastBinario = "";

        for (int i = 0; i < 35; i++) {
            if(redeBinario.charAt(i) == '.'){
                broadcastBinario+=".";
            }else if(redeBinario.charAt(i) == '1' || mascaraBinario.charAt(i) == '0'){
                broadcastBinario+=1;
            }else{
                broadcastBinario+=0;
            }
        }

        return broadcastBinario;
    }


    public static double calcularQuantidadeDeHosts(int mascara){
        double totalHosts = Math.pow(2, 32-mascara)-2;

        return totalHosts;
    }

    public static String[] verFaixa(String redeDecimal, String broadcastDecimal){
        String[] minimo = redeDecimal.split("//.");
        minimo[minimo.length-1] = ((int)Integer.parseInt(minimo[minimo.length-1])-(int)1)+"";

        String[] maximo = redeDecimal.split("//.");
        minimo[minimo.length-1] = ((int)Integer.parseInt(minimo[minimo.length-(int)1])+1)+"";

        String faixaBaixa = String.join(".", minimo);
        String faixaAlta = String.join(".", maximo);

        String[] faixaHost = {faixaBaixa, faixaAlta};

        return faixaHost;

    }


}      