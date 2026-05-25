package com.demo.upimesh.service;

import com.demo.upimesh.model.MeshPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Simulates the Bluetooth mesh.
 *
 * Each VirtualDevice represents a phone. The "gossip" step picks pairs of
 * devices that are nearby (we just say all devices are nearby for the demo)
 * and copies packets between them, decrementing TTL each hop.
 *
 * When a device with internet (a "bridge node") holds a packet, the demo's
 * /api/mesh/flush endpoint causes it to actually POST that packet to our
 * backend — simulating the moment a phone walks outside and gets 4G.
 */
@Service
public class MeshSimulatorService {

    private static final Logger log =
            LoggerFactory.getLogger(MeshSimulatorService.class);

    /**
     * LinkedHashMap preserves insertion order,
     * so devices appear in the UI in the same order
     * we add them here.
     */
    private final Map<String, VirtualDevice> devices =
            new LinkedHashMap<>();

    public MeshSimulatorService() {

        // Default scenario:
        // 1 sender phone
        // 3 relay phones
        // 1 bridge phone with internet
        seedDefaultDevices();
    }

    private void seedDefaultDevices() {

        // Internet-connected bridge node
        devices.put(
                "phone-bridge",
                new VirtualDevice("phone-bridge", true)
        );

        // Sender device
        devices.put(
                "phone-node-1",
                new VirtualDevice("phone-node-1", false)
        );

        // Relay devices
        devices.put(
                "phone-relay-1",
                new VirtualDevice("phone-relay-1", false)
        );

        devices.put(
                "phone-relay-2",
                new VirtualDevice("phone-relay-2", false)
        );

        devices.put(
                "phone-relay-3",
                new VirtualDevice("phone-relay-3", false)
        );
    }

    public Collection<VirtualDevice> getDevices() {
        return devices.values();
    }

    public VirtualDevice getDevice(String id) {
        return devices.get(id);
    }

    /**
     * Sender injects an encrypted packet into the mesh network.
     */
    public void inject(String senderDeviceId, MeshPacket packet) {

        VirtualDevice sender = devices.get(senderDeviceId);

        if (sender == null) {
            throw new IllegalArgumentException(
                    "Unknown device: " + senderDeviceId
            );
        }

        sender.hold(packet);

        log.info(
                "Packet {} injected at {} (TTL={})",
                packet.getPacketId().substring(0, 8),
                senderDeviceId,
                packet.getTtl()
        );
    }

    /**
     * One round of mesh gossip.
     *
     * Every device shares packets with nearby devices.
     * TTL decreases after each hop.
     */
    public GossipResult gossipOnce() {

        int transfers = 0;

        List<VirtualDevice> deviceList =
                new ArrayList<>(devices.values());

        /**
         * Snapshot current state so packets
         * don't infinitely spread in one round.
         */
        Map<String, List<MeshPacket>> snapshot =
                new HashMap<>();

        for (VirtualDevice device : deviceList) {

            snapshot.put(
                    device.getDeviceId(),
                    new ArrayList<>(device.getHeldPackets())
            );
        }

        for (VirtualDevice source : deviceList) {

            for (MeshPacket packet :
                    snapshot.get(source.getDeviceId())) {

                // Ignore expired packets
                if (packet.getTtl() <= 0) {
                    continue;
                }

                for (VirtualDevice destination : deviceList) {

                    // Don't send to itself
                    if (destination == source) {
                        continue;
                    }

                    // Avoid duplicate storage
                    if (destination.holds(packet.getPacketId())) {
                        continue;
                    }

                    // Create forwarded packet copy
                    MeshPacket copy = new MeshPacket();

                    copy.setPacketId(packet.getPacketId());
                    copy.setTtl(packet.getTtl() - 1);
                    copy.setCreatedAt(packet.getCreatedAt());
                    copy.setCiphertext(packet.getCiphertext());

                    destination.hold(copy);

                    transfers++;
                }
            }
        }

        log.info(
                "Gossip round complete: {} packet transfers",
                transfers
        );

        return new GossipResult(
                transfers,
                snapshotMap()
        );
    }

    public Map<String, Integer> snapshotMap() {

        Map<String, Integer> map =
                new LinkedHashMap<>();

        for (VirtualDevice device : devices.values()) {

            map.put(
                    device.getDeviceId(),
                    device.packetCount()
            );
        }

        return map;
    }

    /**
     * Collect packets from internet-enabled bridge devices.
     */
    public List<BridgeUpload> collectBridgeUploads() {

        List<BridgeUpload> uploads =
                new ArrayList<>();

        for (VirtualDevice device : devices.values()) {

            if (!device.hasInternet()) {
                continue;
            }

            for (MeshPacket packet : device.getHeldPackets()) {

                uploads.add(
                        new BridgeUpload(
                                device.getDeviceId(),
                                packet
                        )
                );
            }
        }

        return uploads;
    }

    /**
     * Clears all packets from the mesh.
     */
    public void resetMesh() {
        devices.values().forEach(VirtualDevice::clear);
    }

    public record GossipResult(
            int transfers,
            Map<String, Integer> deviceCounts
    ) {}

    public record BridgeUpload(
            String bridgeNodeId,
            MeshPacket packet
    ) {}
}