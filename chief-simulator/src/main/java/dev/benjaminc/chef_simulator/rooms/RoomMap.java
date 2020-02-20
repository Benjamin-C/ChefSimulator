package dev.benjaminc.chef_simulator.rooms;

import java.util.List;

import dev.benjaminc.chef_simulator.Score;
import dev.benjaminc.chef_simulator.control.Chef;

public interface RoomMap {

	public Room getRoom(List<Chef> cooks, Score score);
}
