package com.cafedeflore.libNHD;

import java.util.Map;

import com.cafedeflore.mininhd.R;

import com.cafedeflore.libNHD.data.TorrentEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Tools {
	public static int getUserProfile(String html){
		Document doc = Jsoup.parse(html);
		Element info_block = doc.getElementById("info_block");
		return 0;
	} 
	
	public static String torrentToEntity(Element torrent, TorrentEntity torrentEntity){
		//System.out.println(torrent.toString());
		
		String[] s = {"c_movies", "c_tvseries", "c_tvshows", "c_doc", "c_anime", "c_sports", "c_mv", "c_hqaudio", "c_other", "c_games", "c_elearning"};
		int[] t = {R.drawable.cat101, R.drawable.cat102,
				R.drawable.cat103, R.drawable.cat104, R.drawable.cat105, R.drawable.cat106,
				R.drawable.cat107, R.drawable.cat108, R.drawable.cat109, R.drawable.cat110, R.drawable.cat111};
		//type
		Element typeItem = torrent.child(0);
		torrentEntity.setType(t[0]);
		for(int i = 0;i < s.length; ++i){
			if(s[i].equals(typeItem.select("img[class]").first().className())){
				torrentEntity.setType(t[i]);
				break;
			}
		}
		
		//title & second title
		Element nameItem = torrent.child(1);
		torrentEntity.setTitle(nameItem.select("a[title]").text());
		
		nameItem = nameItem.select("td.embedded").first();
		if(nameItem.textNodes().size() < 1)
			torrentEntity.setSecondTitle("");
		else
			torrentEntity.setSecondTitle( nameItem.textNodes().get(nameItem.textNodes().size()-1).toString());		//to be bug fixed
		
		//time
		Element time = torrent.child(3);
		torrentEntity.setTime(time.select("span[title]").text() );
		
		//size
		Element size = torrent.child(4);
		torrentEntity.setSize(size.text());
		
		//upload
		Element upload = torrent.child(5);
		if(upload.text() != null)
			torrentEntity.setTorrentNumber(Integer.parseInt(upload.text().replace(",", "")));
		//System.out.println(upload.text());
		//download
		Element download = torrent.child(6);
		if(download.text() != null)
			torrentEntity.setDownloadNumber(Integer.parseInt(download.text().replace(",", "")));
		
		//upload
		Element finished = torrent.child(7);
		if(finished.text() != null)
			torrentEntity.setCompleteNumber(Integer.parseInt(finished.text().replace(",", "")));
		
		return "";
	}
}
