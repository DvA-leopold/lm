package converters;

public interface Converter {
    /**
     * @param dirName full path to the input folder with images to convert
     * @param outputFileName output file name, resulting name will be look like this
     *                       dirname + outputFileName + .tif, and will be stored on the
     *                       upper folder.
     * @return <code>true</code> if conversion was ok, <code>false</code> respectively
     */
    public boolean convert(final String dirName, final String outputFileName);
}
