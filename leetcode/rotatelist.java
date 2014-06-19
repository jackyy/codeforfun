/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode rotateRight(ListNode head, int n) {
        if(head==null){
            return null;
        }
        
        int len=1;
        ListNode curr=head;
        ListNode tail=null;
        while(curr.next!=null)
        {
            len++;
            curr=curr.next;
        }
        tail=curr;
        
        int skip = (len - (n % len))%len;
        ListNode newHead=head;
        ListNode last=tail;
        while(skip>0)
        {
            skip--;
            last=newHead;
            newHead=newHead.next;
        }

        tail.next=head;
        last.next=null;

        return newHead;
    }
}
