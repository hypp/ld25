package com.kompetensum.villain;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class Sounds {

	public static Sound player_falling;
	public static Array<Sound> mjau = new Array<Sound>();
	public static Sound player_haha;
	public static Sound player_boing;
	public static Sound player_lala;
	public static Sound music;
	
	public static void load()
	{
		player_falling = Gdx.audio.newSound(Gdx.files.internal("audio/josse_oha.ogg"));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_1.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_2.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_3.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_4.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_5.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_6.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_7.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_8.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/josse_mjau_9.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/carro_mjau_1.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/carro_mjau_2.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/carro_mjau_3.ogg")));
		mjau.add(Gdx.audio.newSound(Gdx.files.internal("audio/carro_mjau_4.ogg")));
		player_haha = Gdx.audio.newSound(Gdx.files.internal("audio/josse_haha.ogg"));
		player_boing = Gdx.audio.newSound(Gdx.files.internal("audio/carro_boing.ogg"));
		player_lala = Gdx.audio.newSound(Gdx.files.internal("audio/carro_lalala.ogg"));
		music = Gdx.audio.newSound(Gdx.files.internal("audio/villain.ogg"));
		
	}
	
}
