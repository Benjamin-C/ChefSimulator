package benjaminc.chief_simulator.rooms;

import java.util.List;

import benjaminc.chief_simulator.Score;
import benjaminc.chief_simulator.control.Cook;
import benjaminc.chief_simulator.graphics.Room;

public interface RoomMap {

	public Room getRoom(List<Cook> cooks, Score score);
}
