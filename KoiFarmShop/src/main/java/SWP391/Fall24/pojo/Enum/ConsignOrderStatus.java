package SWP391.Fall24.pojo.Enum;

public enum ConsignOrderStatus {
    Pending_confirmation, Receiving, Responded, Rejected, Done, Shared, Expired; // Done is Sold status
    // Sold is status that consigned fish is sold and our shop has not shared profit
    // Shared is completed status that means our shop shared profit
}
