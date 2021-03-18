package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class App {

	static private Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		FTPClient client = new FTPClient();

		try {
			client.connect("localhost");

			String user = "";
			String password = "";

			while (!client.login(user, password)) {
				System.out.println("Usuario");
				user = sc.nextLine();
				System.out.println("Password");
				password = sc.nextLine();
			}

			System.out.println("Conexion correcta");

			// Listar ficheros y directorio
			System.out.println("Directorio actual: " + client.printWorkingDirectory());
			FTPFile[] files = client.listFiles();
			System.out.println("Ficheros en el directorio actual: " + files.length);

			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
			}

			// Subir fichero
			InputStream input = new FileInputStream("C:\\Users\\pipe4\\Desktop\\hola.txt");
			client.storeFile("hola.txt", input);
			System.out.println("Fichero subido con exito");

			// DescargarArchivos
			OutputStream output = new FileOutputStream("C:\\Users\\pipe4\\Desktop\\descarga.txt");
			client.retrieveFile("descarga.txt", output);
			System.out.println("Fichero descargado");

			// Eliminar archivo
			System.out.println("Dime el nombre del archivo que deseas eliminar");
			client.deleteFile(sc.nextLine());

			// Crear directorio
			System.out.println("Directorio que desea crear");
			client.makeDirectory(sc.nextLine());

			// Eliminar directorio
			System.out.println("Directorio que desea eliminar");
			client.removeDirectory(sc.nextLine());

			// Cambiar directorio
			System.out.println("Nuevo directorio");
			String directorio = sc.nextLine();

			if (directorio.equals("..")) {
				client.changeToParentDirectory();
			} else {
				client.changeWorkingDirectory(directorio);
			}

			System.out.println("Directorio actual: " + client.printWorkingDirectory());

			// Desconexion
			client.disconnect();
			System.out.println("Conexion cerrada");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
