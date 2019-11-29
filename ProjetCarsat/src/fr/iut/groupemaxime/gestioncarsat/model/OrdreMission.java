package fr.iut.groupemaxime.gestioncarsat.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.interfaces.DocJson;

public class OrdreMission implements DocJson<OrdreMission> {

	private Agent agent;
	private Mission mission;
	private Transport transport;
	private String fichier;

	public OrdreMission(Agent agent, Mission mission, Transport transport, String fichier) {
		this.agent = agent;
		this.mission = mission;
		this.transport = transport;
		this.fichier = fichier;
	}

	public OrdreMission(Agent agent, Mission mission, Transport transport) {
		this(agent, mission, transport, null);
	}

	public OrdreMission() {
		this(null, null, null, null);
	}

	@Override
	public void sauvegarderJson(String adresseFichier) {
		this.fichier = adresseFichier;
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Gson gson = new GsonBuilder().registerTypeAdapter(Mission.class, new InterfaceAdapter())
				.registerTypeAdapter(Transport.class, new InterfaceAdapter()).setPrettyPrinting().create();
		String s = gson.toJson(this);
		FileWriter f;
		try {
			f = new FileWriter(new File(adresseFichier));
			f.write(s);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public OrdreMission chargerJson(String adresseFichier) {
		Gson g = new Gson();
		Gson gson = new GsonBuilder().registerTypeAdapter(Mission.class, new InterfaceAdapter())
				.registerTypeAdapter(Transport.class, new InterfaceAdapter()).setPrettyPrinting().create();
		OrdreMission om = new OrdreMission();
		InputStream is;
		try {
			is = new FileInputStream(new File(adresseFichier));
			// Creation du JsonReader depuis Json.
			JsonReader reader = Json.createReader(is);
			// Recuperer la structure JsonObject depuis le JsonReader.
			JsonObject objetJson = reader.readObject();
			reader.close();
			om = gson.fromJson(objetJson.toString(), OrdreMission.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return om;
	}

	public Agent getAgent() {
		return agent;
	}

	public Mission getMission() {
		return mission;
	}

	public Transport getTransport() {
		return transport;
	}

	public String getFichier() {
		return fichier;
	}
}
