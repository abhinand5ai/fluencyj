package com.abhinand.systemdesign.loadbalancer;

import java.util.*;
import java.util.stream.Collectors;

class Machine {
    private final int machineId;
    private final int capacity;

    private int remainingCapacity;

    private final List<ApplicationRequirement> appRequirements;

    public Machine(int machineId, int capacity) {
        this.machineId = machineId;
        this.capacity = capacity;
        this.remainingCapacity = capacity;
        appRequirements = new ArrayList<>();
    }

    public int getMachineId() {
        return machineId;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void addApplication(int appId, int loadUse) {
        appRequirements.add(new ApplicationRequirement(appId, loadUse));
        remainingCapacity -= loadUse;
    }

    public List<ApplicationRequirement> getAppRequirements() {
        return appRequirements;
    }

    public void stopApplication(int appId) {
        appRequirements.stream()
                .filter(appReq1 -> appReq1.appId() == appId)
                .findAny()
                .ifPresent(appReq -> {
                            appRequirements.remove(appReq);
                            remainingCapacity += appReq.loadUse();
                        }
                );
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machineId=" + machineId +
                ", capacity=" + capacity +
                ", remainingCapacity=" + remainingCapacity +
                '}';
    }
}

record ApplicationRequirement(int appId, int loadUse) {
}

class DCLoadBalancer {


    private final HashMap<Integer, Integer> appRegistry;
    private final HashMap<Integer, Machine> machineRegistry;

    public DCLoadBalancer() {
        machineRegistry = new HashMap<>();
        appRegistry = new HashMap<>();
    }

    public void addMachine(int machineId, int capacity) {
        Machine machine = new Machine(machineId, capacity);

        machineRegistry.put(machineId, machine);
    }

    public void removeMachine(int machineId) {
        Machine machine = machineRegistry.get(machineId);
        machineRegistry.remove(machineId);
        List<ApplicationRequirement> applications = machine.getAppRequirements();
        applications.forEach(appReq -> addApplication(appReq.appId(), appReq.loadUse()));


    }

    public int addApplication(int appId, int loadUse) {
        Comparator<Machine> machineComparator = (x, y) -> x.getRemainingCapacity() == y.getRemainingCapacity()
                ? Integer.compare(x.getMachineId(), y.getMachineId())
                : Integer.compare(-x.getRemainingCapacity(), -y.getRemainingCapacity());

        return machineRegistry.values().stream().min(machineComparator)
                .filter(machine -> machine.getRemainingCapacity() >= loadUse)
                .map(machine -> {
                    machine.addApplication(appId, loadUse);
                    appRegistry.put(appId, machine.getMachineId());
                    return machine.getMachineId();
                }).orElse(-1);

    }

    public void stopApplication(int appId) {
        Integer machineId = appRegistry.get(appId);
        Machine machine = machineRegistry.get(machineId);
        machine.stopApplication(appId);
    }

    public List<Integer> getApplications(int machineId) {
        return machineRegistry.get(machineId)
                .getAppRequirements().stream()
                .map(ApplicationRequirement::appId)
                .limit(10)
                .collect(Collectors.toList());
    }
}

class Main {
    public static void main(String[] args) {
        DCLoadBalancer dcLoadBalancer = new DCLoadBalancer();
        int[][] machines = {
                {1, 1}, {2, 10}, {3, 10}, {4, 15}

        };
        Arrays.stream(machines).forEach(entry -> dcLoadBalancer.addMachine(entry[0], entry[1]));

        int[][] applications = {
                {1, 3}, {2, 11}, {3, 6}, {4, 5}
        };

        Arrays.stream(applications).forEach(entry -> {
            int machineId = dcLoadBalancer.addApplication(entry[0], entry[1]);
            System.out.println(machineId);

        });

        System.out.println(Arrays.toString(dcLoadBalancer.getApplications(2).toArray()));

        dcLoadBalancer.addMachine(5, 10);

        int machineId = dcLoadBalancer.addApplication(5, 5);
        System.out.println(machineId);
        dcLoadBalancer.stopApplication(3);


    }
}

/**
 * Your DCLoadBalancer object will be instantiated and called as such:
 * DCLoadBalancer obj = new DCLoadBalancer();
 * obj.addMachine(machineId,capacity);
 * obj.removeMachine(machineId);
 * int param_3 = obj.addApplication(appId,loadUse);
 * obj.stopApplication(appId);
 * List<Integer> param_5 = obj.getApplications(machineId);
 */
