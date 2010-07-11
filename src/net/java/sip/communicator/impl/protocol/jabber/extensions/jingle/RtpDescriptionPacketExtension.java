/*
 * SIP Communicator, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.protocol.jabber.extensions.jingle;

import java.util.*;

import net.java.sip.communicator.impl.protocol.jabber.extensions.*;

import org.jivesoftware.smack.packet.*;

/**
 * Represents the content <tt>description</tt> elements described in XEP-0167.
 *
 * @author Emil Ivov
 */
public class RtpDescriptionPacketExtension
    extends AbstractPacketExtension
{
    /**
     * The name space for RTP description elements.
     */
    public static final String NAMESPACE = "urn:xmpp:jingle:apps:rtp:1";

    /**
     * The name of the "description" element.
     */
    public static final String ELEMENT_NAME = "description";

    /**
     * The name of the <tt>media</tt> description argument.
     */
    public static final String MEDIA_ATTR_NAME = "media";

    /**
     * The name of the <tt>ssrc</tt> description argument.
     */
    public static final String SSRC_ATTR_NAME = "ssrc";

    /**
     * The list of payload types that this description element contains.
     */
    private final List<PayloadTypePacketExtension> payloadTypes
                                = new ArrayList<PayloadTypePacketExtension>();

    /**
     * An optional encryption element that contains encryption parameters for
     * this session.
     */
    private EncryptionPacketExtension encryption;

    /**
     * An optional bandwidth element that specifies the allowable or preferred
     * bandwidth for use by this application type.
     */
    private BandwidthPacketExtension bandwidth;

    /**
     * The combined list of all child elements that this extension contains.
     */
    private List<PacketExtension> children;

    /**
     * Creates a new <tt>RtpDescriptionPacketExtension</tt>.
     */
    public RtpDescriptionPacketExtension()
    {
        super(NAMESPACE, ELEMENT_NAME);
    }

    /**
     * Specifies the media type for the stream that this description element
     * represents, such as "audio" or "video".
     *
     * @param media the media type for the stream that this element represents
     * such as "audio" or "video".
     */
    public void setMedia(String media)
    {
        super.setAttribute(MEDIA_ATTR_NAME, media);
    }

    /**
     * Returns the media type for the stream that this description element
     * represents, such as "audio" or "video".
     *
     * @return  the media type for the stream that this description element
     * represents, such as "audio" or "video".
     */
    public String getMedia()
    {
        return getAttributeAsString(MEDIA_ATTR_NAME);
    }

    /**
     * Sets the synchronization source ID (SSRC as per RFC 3550) that the stream
     * represented by this description element will be using.
     *
     * @param ssrc the SSRC ID that the RTP stream represented here will be
     * using.
     */
    public void setSsrc(String ssrc)
    {
        super.setAttribute(SSRC_ATTR_NAME, ssrc);
    }

    /**
     * Returns the synchronization source ID (SSRC as per RFC 3550) that the
     * stream represented by this description element will be using.
     *
     * @return the synchronization source ID (SSRC as per RFC 3550) that the
     * stream represented by this description element will be using.
     */
    public String getSsrc()
    {
        return getAttributeAsString(SSRC_ATTR_NAME);
    }

    /**
     * Adds a new payload type to this description element.
     *
     * @param payloadType the new payload to add.
     */
    public void addPayloadType(PayloadTypePacketExtension payloadType)
    {
        this.payloadTypes.add(payloadType);
    }

    /**
     * Returns a <b>reference</b> to the list of payload types that we have
     * registered with this description so far.
     *
     * @return a <b>reference</b> to the list of payload types that we have
     * registered with this description so far.
     */
    public List<PayloadTypePacketExtension> getPayloadTypes()
    {
        return payloadTypes;
    }

    /**
     * Returns all child elements that we currently have in this packet.
     *
     * @return the {@link List} of child elements currently registered with
     * this packet.
     */
    @Override
    public List<? extends PacketExtension> getChildExtensions()
    {
        if(children == null)
            children = new ArrayList<PacketExtension>();
        else
            children.clear();

        //payload types
        children.addAll(payloadTypes);

        //encryption element
        if (encryption != null)
            children.add(encryption);

        //bandwidth element
        if (bandwidth != null)
            children.add(bandwidth);

        return children;
    }

    /**
     * Casts <tt>childExtension</tt> to one of the extensions allowed here and
     * sets the corresponding field.
     *
     * @param childExtension the extension we'd like to add here.
     */
    public void addChildExtension(PacketExtension childExtension)
    {
        if(childExtension instanceof PayloadTypePacketExtension)
            this.addPayloadType((PayloadTypePacketExtension)childExtension);

        else if (childExtension instanceof EncryptionPacketExtension)
            this.setEncryption((EncryptionPacketExtension)childExtension);

        else if (childExtension instanceof BandwidthPacketExtension)
            this.setBandwidth((BandwidthPacketExtension)childExtension);
    }

    /**
     * Sets the optional encryption element that contains encryption parameters
     * for this session.
     *
     * @param encryption the encryption {@link PacketExtension} we'd like to add
     * to this packet.
     */
    public void setEncryption(EncryptionPacketExtension encryption)
    {
        this.encryption = encryption;
    }

    /**
     * Returns the optional encryption element that contains encryption
     * parameters for this session.
     *
     * @return the encryption {@link PacketExtension} added to this packet or
     * <tt>null</tt> if none has been set yet.
     */
    public EncryptionPacketExtension getEncryption()
    {
        return encryption;
    }

    /**
     * Sets an optional bandwidth element that specifies the allowable or
     * preferred bandwidth for use by this application type.
     *
     * @param bandwidth the max/preferred bandwidth indication that we'd like
     * to add to this packet.
     */
    public void setBandwidth(BandwidthPacketExtension bandwidth)
    {
        this.bandwidth = bandwidth;
    }

    /**
     * Returns an optional bandwidth element that specifies the allowable or
     * preferred bandwidth for use by this application type.
     *
     * @return the max/preferred bandwidth set for this session or <tt>null</tt>
     * if none has been set yet.
     */
    public BandwidthPacketExtension getBandwidth()
    {
        return bandwidth;
    }
}