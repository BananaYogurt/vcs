package com.vcs.util;

import com.vcs.components.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * created by wuzh on 2017/5/6 21:09
 */
public class FindBranch {

    public static Branch findBranchByName(String name) throws DocumentException {

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(Env.branches);

        Element root = document.getRootElement();

        List<Element> list = root.elements("branch");

        Branch branch = new Branch();

        for(Element e : list){

            if (e.attribute("name").getValue().equals(name)) {

                branch.setName(e.attribute("name").getValue());

                branch.setNotNull(Boolean.parseBoolean(e.attribute("not-null").getValue()));

                branch.setVersionPath(e.attribute("versionPath").getValue());

                System.out.println(branch.toString());

                return branch;
            }

        }

        return null;
    }

}
