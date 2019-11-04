package benjaminc.chef_simulator.data.keys;

import benjaminc.chef_simulator.Objective;
import benjaminc.chef_simulator.things.Thing;

/**
 * Data keys for saving Rooms to JSON
 * @author Benjamin-C
 *
 */
public enum RoomDataKey {
/** the JSON 2d array of {@link Thing} in the room */
ROOM,
/** the JSON array of {@link Objective} */
OBJECTIVES;
}
