package dns_resolver;

/**
 * The IPAddress is using iIPv4 and has dotted-decimal notation, with the network, two subnets,
 * and host separated by periods. For example, the IP address 130.191.226.146 has
 * a network of 130, a subnet of 191, the second subnet is 226, and the host address is 146.
 * <p>
 * Your IPAddress class should accept a string of dotted-decimal IPAddresses in the constructor
 * and separate them into the components.
 *
 * @author Khaled Elsayed
 * CS 310 - Edwards
 * @see <a href="https://en.wikipedia.org/wiki/IP_address#IPv4_addresses">Wikipedia IPv4 addresses</a>
 */

public class IPAddress implements Comparable<IPAddress> {

    int network;
    int subnet;
    int subnet2;
    int host;
    String ip;

    /**
     * The constructor for the IPAddress class
     *
     * @param ip the dotted-decimal IP address
     */
    public IPAddress(String ip) {
        String data[] = ip.split("\\.");

        this.ip = ip;
        network = Integer.parseInt(data[0]);
        subnet = Integer.parseInt(data[1]);
        subnet2 = Integer.parseInt(data[2]);
        host = Integer.parseInt(data[3]);
    }


    /**
     * @return hash code of the IP Address components
     */
    @Override
    public int hashCode() {
        return network + subnet + subnet2 + host;
    }

    /**
     * @param obj, the Object to test
     * @return true if both objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.toString().equals(this.toString()))
            return true;

        return false;
    }

    /**
     * @return String representation of IP Address components
     */
    @Override
    public String toString() {
        return network + "." + subnet + "." + subnet2 + "." + host;
    }

    @Override
    public int compareTo(IPAddress obj) {
        return this.ip.compareTo(obj.ip);
    }
}
