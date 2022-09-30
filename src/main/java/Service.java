import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;
import java.security.SecureRandom;

@WebService(name = "Service", targetNamespace = "utez")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Service {
    @WebMethod(operationName = "responsesMessage")
    public String responseMessage(@WebParam(name = "message") String message){
        return "El mensaje recibido fue " + message;
    }

    @WebMethod(operationName = "numerosAleatorios")
    public String numerosAleatorios(@WebParam(name = "numero") int numero){
        int x = (int) (Math.random() * ((10 - 1) + 1)) + 1;
        if (numero == x){
            return "Numero Correcto";
        }else{
            return "Vuelve a intentarlo";
        }
    }

    @WebMethod(operationName = "eliminarVocales")
    public String eliminarVocales(@WebParam(name = "palabra") String palabra){
        String cadenaCons = palabra.replaceAll("[a,á,e,é,i,í,o,ó,u,ú,A,E,I,O,U]", "");
        return "La palabra sin vocales es: " + cadenaCons;
    }

    @WebMethod(operationName = "rfc")
    public String rfc(@WebParam(name = "nombre") String nombre, @WebParam(name = "apePa") String apePa, @WebParam(name = "apeMa") String apeMa, @WebParam(name = "año") String año, @WebParam(name = "mes") String mes, @WebParam(name = "dia") String dia){
        String fecha_nac = año + mes + dia;
        String nomLetra = nombre.substring(0,1);
        String apePaLetra = apePa.substring(0,2);
        String apeMaLetra = apeMa.substring(0,1);
        String fecha = fecha_nac.substring(2,8);
        String random = generateRandomString(3);
        String rfc = apePaLetra + apeMaLetra + nomLetra + fecha + random;
        return rfc.toUpperCase();
    }

    public String generateRandomString(int length) {
        // Puede personalizar los personajes que desea agregar a
        // las cadenas al azar
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";
        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();
        if (length < 1) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), retornos aleatorios 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Iniciando el servidor...");
        Endpoint.publish("http://localhost:8080/Service", new Service());
        System.out.println("Esperando peticion...");
    }
}
