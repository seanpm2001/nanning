/*
 * Nanning Aspects
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package com.tirsen.nanning.attribute;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.parser.impl.JFlexLexer;
import com.thoughtworks.qdox.parser.impl.Parser;
import com.thoughtworks.qdox.model.*;

import java.io.*;
import java.util.Properties;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;

/**
 * TODO document AttributesCompiler
 *
 * <!-- $Id: AttributesCompiler.java,v 1.1 2003-01-12 13:25:40 tirsen Exp $ -->
 *
 * @author $Author: tirsen $
 * @version $Revision: 1.1 $
 */
public class AttributesCompiler extends Task {

	private File src;
	private File dest;
	private AttributesBuilder builder = new AttributesBuilder();

	public void setSrc(File src) {
		this.src = src;
	}

	public void setDest(File dest) {
		this.dest = dest;
	}

	public void execute() {
		try {
			System.out.println("Compiling attributes for " + src + " into " + dest);
			String[] files = getJavaFiles();
			for (int i = 0; i < files.length; i++) {
				final File javaFile = new File(src, files[i]);
				final File attributeFile = getAttributeFile(files[i]);
				if (!attributeFile.exists() || attributeFile.lastModified() < javaFile.lastModified()) {
					createAttributesFile(javaFile, attributeFile);
				}
			}
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}

	private String[] getJavaFiles() {
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(src);
		scanner.setIncludes(new String[]{"**/*.java"});
		scanner.scan();
		return scanner.getIncludedFiles();
	}

	private File getAttributeFile(String javaFileName) {
		File result = new File(dest, javaFileName.substring(0, javaFileName.length() - 5) + ".attributes");
		result.getParentFile().mkdirs();
		return result;
	}

	private void createAttributesFile(File javaFile, File attributeFile) throws IOException {
		Properties attributes = parseAttributeProperties(javaFile);
		OutputStream output = new FileOutputStream(attributeFile);
		try {
			attributes.store(output, javaFile.getName());
		} finally {
			output.close();
		}
	}

	private Properties parseAttributeProperties(File javaFile) throws IOException {
		InputStream input = new FileInputStream(javaFile);
		try {
			builder.reset();
			new Parser(new JFlexLexer(input), builder).parse();
			return builder.getProperties();
		} finally {
			input.close();
		}
	}

}