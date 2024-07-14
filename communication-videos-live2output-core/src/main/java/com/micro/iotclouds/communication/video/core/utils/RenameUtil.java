//package com.micro.iotclouds.communication.video.core.utils;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Method;
//
//import ch.qos.logback.core.rolling.RollingFileAppender;
//import ch.qos.logback.core.rolling.RolloverFailure;
//import cn.hutool.core.io.FileUtil;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Utility class to help solving problems encountered while renaming files.
// *
// * @author Ceki Gulcu
// */
//@Slf4j
//public class RenameUtil {
//
//	static final int BUF_SIZE = 32 * 1024;
//
//	static final String PATH_CLASS_STR = "java.nio.file.Path";
//
//	static final String FILES_CLASS_STR = "java.nio.file.Files";
//
////	static String RENAMING_ERROR_URL = CoreConstants.CODES_URL + "#renamingError";
//
//	/**
//	 * A relatively robust file renaming method which in case of failure due to src
//	 * and target being on different volumes, falls back onto renaming by copying.
//	 *
//	 * @param src
//	 * @param target
//	 * @throws RolloverFailure
//	 */
//	public static void rename(String src, String target) throws RolloverFailure {
//		if (src.equals(target)) {
//			log.warn("Source and target files are the same [" + src + "]. Skipping.");
//			return;
//		}
//		File srcFile = new File(src);
//
//		if (srcFile.exists()) {
//			File targetFile = new File(target);
//			createMissingTargetDirsIfNecessary(targetFile);
//
//			log.info("Renaming file [" + srcFile + "] to [" + targetFile + "]");
//
//			boolean result = srcFile.renameTo(targetFile);
//
//			if (!result) {
//				log.error("Failed to rename file [" + srcFile + "] as [" + targetFile + "].");
//				Boolean areOnDifferentVolumes = areOnDifferentVolumes(srcFile, targetFile);
//				if (Boolean.TRUE.equals(areOnDifferentVolumes)) {
//					log.warn("Detected different file systems for source [" + src + "] and target [" + target
//							+ "]. Attempting rename by copying.");
//					renameByCopying(src, target);
//					return;
//				} else {
//					log.warn("Please consider leaving the [file] option of " + RollingFileAppender.class.getSimpleName()
//							+ " empty.");
//				}
//			}
//		} else {
//			throw new RolloverFailure("File [" + src + "] does not exist.");
//		}
//	}
//
//	/**
//	 * Attempts to determine whether both files are on different volumes. Returns
//	 * true if we could determine that the files are on different volumes. Returns
//	 * false otherwise or if an error occurred while doing the check.
//	 *
//	 * @param srcFile
//	 * @param targetFile
//	 * @return true if on different volumes, false otherwise or if an error occurred
//	 */
//	static Boolean areOnDifferentVolumes(File srcFile, File targetFile) throws RolloverFailure {
//		if (!EnvUtil.isJDK7OrHigher())
//			return false;
//
//		// target file is not certain to exist but its parent has to exist given the
//		// call hierarchy of this method
//		File parentOfTarget = targetFile.getAbsoluteFile().getParentFile();
//
//		if (parentOfTarget == null) {
//			log.warn("Parent of target file [" + targetFile + "] is null");
//			return null;
//		}
//		if (!parentOfTarget.exists()) {
//			log.warn("Parent of target file [" + targetFile + "] does not exist");
//			return null;
//		}
//
//		try {
//			boolean onSameFileStore = areOnSameFileStore(srcFile, parentOfTarget);
//			return !onSameFileStore;
//		} catch (RolloverFailure rf) {
//			log.warn("Error while checking file store equality", rf);
//			return null;
//		}
//	}
//
//	/**
//	 * This method assumes that both files a and b exists.
//	 *
//	 * @param a
//	 * @param b
//	 * @return
//	 * @throws Exception
//	 */
//	static public boolean areOnSameFileStore(File a, File b) throws RolloverFailure {
//		if (!a.exists()) {
//			throw new IllegalArgumentException("File [" + a + "] does not exist.");
//		}
//		if (!b.exists()) {
//			throw new IllegalArgumentException("File [" + b + "] does not exist.");
//		}
//
//		// Implements the following by reflection
//		// Path pathA = a.toPath();
//		// Path pathB = b.toPath();
//		//
//		// FileStore fileStoreA = Files.getFileStore(pathA);
//		// FileStore fileStoreB = Files.getFileStore(pathB);
//		//
//		// return fileStoreA.equals(fileStoreB);
//
//		try {
//			Class<?> pathClass = Class.forName(PATH_CLASS_STR);
//			Class<?> filesClass = Class.forName(FILES_CLASS_STR);
//
//			Method toPath = File.class.getMethod("toPath");
//			Method getFileStoreMethod = filesClass.getMethod("getFileStore", pathClass);
//
//			Object pathA = toPath.invoke(a);
//			Object pathB = toPath.invoke(b);
//
//			Object fileStoreA = getFileStoreMethod.invoke(null, pathA);
//			Object fileStoreB = getFileStoreMethod.invoke(null, pathB);
//			return fileStoreA.equals(fileStoreB);
//		} catch (Exception e) {
//			throw new RolloverFailure("Failed to check file store equality for [" + a + "] and [" + b + "]", e);
//		}
//	}
//
//	/**
//	 * 通过复制重命名文件
//	 * 
//	 * @param src
//	 * @param target
//	 * @throws RolloverFailure
//	 */
//	public static void renameByCopying(String src, String target) throws RolloverFailure {
//
//		copy(src, target);
//
//		File srcFile = new File(src);
//		if (!srcFile.delete()) {
//			log.error("Could not delete " + src);
//		}
//
//	}
//
//	/**
//	 * 通过复制重命名文件
//	 * 
//	 * @param src
//	 * @param target
//	 * @throws RolloverFailure
//	 */
//	public static void rename4New(String src, String target) throws RolloverFailure {
//
////		copy(src, target);
//		File srcFile = new File(src);
//
//		if (srcFile.exists()) {
//			File targetFile = new File(target);
//			createMissingTargetDirsIfNecessary(targetFile);
//
//			log.info("Renaming file [" + srcFile + "] to [" + targetFile + "]");
//
//			boolean result = srcFile.renameTo(targetFile);
//			System.out.println(result);
//			if (!result) {// 没有成功，强制改名
////				if(!FileUtil.isFileExists(target)){
//				FileUtil.copy(srcFile, targetFile, true);
//				FileUtil.del(srcFile);
////				}
//			}
//		}
//
//	}
//
//	/**
//	 * 复制文件
//	 * 
//	 * @param src
//	 * @param destination
//	 * @throws RolloverFailure
//	 */
//	public static void copy(String src, String destination) throws RolloverFailure {
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//		try {
//			bis = new BufferedInputStream(new FileInputStream(src));
//			bos = new BufferedOutputStream(new FileOutputStream(destination));
//			byte[] inbuf = new byte[BUF_SIZE];
//			int n;
//
//			while ((n = bis.read(inbuf)) != -1) {
//				bos.write(inbuf, 0, n);
//			}
//
//			bis.close();
//			bis = null;
//			bos.close();
//			bos = null;
//		} catch (IOException ioe) {
//			String msg = "Failed to copy [" + src + "] to [" + destination + "]";
//			log.error(msg, ioe);
//			throw new RolloverFailure(msg);
//		} finally {
//			if (bis != null) {
//				try {
//					bis.close();
//				} catch (IOException e) {
//					// ignore
//				}
//			}
//			if (bos != null) {
//				try {
//					bos.close();
//				} catch (IOException e) {
//					// ignore
//				}
//			}
//		}
//	}
//
//	/**
//	 * 清空文件内容
//	 * 
//	 * @param fileName
//	 */
//	public static void clearInfoForFile(String fileName) {
//		File file = new File(fileName);
//		try {
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			FileWriter fileWriter = new FileWriter(file);
//			fileWriter.write("");
//			fileWriter.flush();
//			fileWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	static void createMissingTargetDirsIfNecessary(File toFile) throws RolloverFailure {
//		boolean result = createMissingParentDirectories(toFile);
//		if (!result) {
//			throw new RolloverFailure("Failed to create parent directories for [" + toFile.getAbsolutePath() + "]");
//		}
//	}
//
//	/**
//	 * Creates the parent directories of a file. If parent directories not specified
//	 * in file's path, then nothing is done and this returns gracefully.
//	 *
//	 * @param file file whose parent directories (if any) should be created
//	 * @return {@code true} if either no parents were specified, or if all parent
//	 *         directories were created successfully; {@code false} otherwise
//	 */
//	static public boolean createMissingParentDirectories(File file) {
//		File parent = file.getParentFile();
//		if (parent == null) {
//			// Parent directory not specified, therefore it's a request to
//			// create nothing. Done! ;)
//			return true;
//		}
//
//		// File.mkdirs() creates the parent directories only if they don't
//		// already exist; and it's okay if they do.
//		parent.mkdirs();
//		return parent.exists();
//	}
//
////    @Override
////    public String toString() {
////        return "c.q.l.co.rolling.helper.RenameUtil";
////    }
//}
