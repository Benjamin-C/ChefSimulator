package dev.orangeben.chef_simulator.rooms;

import java.util.List;

import dev.orangeben.chef_simulator.Score;
import dev.orangeben.chef_simulator.control.Chef;

public interface RoomMap {

	public Room getRoom(List<Chef> cooks, Score score);
}
