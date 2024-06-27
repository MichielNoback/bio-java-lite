/**
 *
 */
package net.cellingo.sequence_tools.sequences;

import java.util.HashMap;

/**
 * class specifies selections sequence frames
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum SequenceFrame {
    FRAME1FOR("frame 1 forward"),
    FRAME2FOR("frame 2 forward"),
    FRAME3FOR("frame 3 forward"),
    FRAME1REV("frame 1 reverse"),
    FRAME2REV("frame 2 reverse"),
    FRAME3REV("frame 3 reverse"),
    UNKNOWN("unknown frame");

    private String frame;
    private static HashMap<Integer, SequenceFrame> val2enum;
    private static HashMap<SequenceFrame, Integer> enum2val;

    static {
        val2enum = new HashMap<Integer, SequenceFrame>();
        val2enum.put(1, SequenceFrame.FRAME1FOR);
        val2enum.put(2, SequenceFrame.FRAME2FOR);
        val2enum.put(3, SequenceFrame.FRAME3FOR);
        val2enum.put(4, SequenceFrame.FRAME1REV);
        val2enum.put(5, SequenceFrame.FRAME2REV);
        val2enum.put(6, SequenceFrame.FRAME3REV);
        val2enum.put(0, SequenceFrame.UNKNOWN);

        enum2val = new HashMap<SequenceFrame, Integer>();
        enum2val.put(SequenceFrame.FRAME1FOR, 1);
        enum2val.put(SequenceFrame.FRAME2FOR, 2);
        enum2val.put(SequenceFrame.FRAME3FOR, 3);
        enum2val.put(SequenceFrame.FRAME1REV, 4);
        enum2val.put(SequenceFrame.FRAME2REV, 5);
        enum2val.put(SequenceFrame.FRAME3REV, 6);
        enum2val.put(SequenceFrame.UNKNOWN, 0);
    }

    private SequenceFrame(String frame) {
        this.frame = frame;
    }

    public String toString() {
        return frame;
    }

    /**
     * get the frame enum
     *
     * @param frame
     * @return sequenceFrame
     */
    public static SequenceFrame getSequenceFrame(int frame) {
        if (val2enum.containsKey(frame)) {
            return val2enum.get(frame);
        } else {
            return SequenceFrame.UNKNOWN;
        }
    }

    /**
     * get the frame number
     *
     * @param frame
     * @return frameNumber
     */
    public static int getFrameNumber(SequenceFrame frame) {
        return enum2val.get(frame);
    }

}

