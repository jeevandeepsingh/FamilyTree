package TheFamily;

import static TheFamily.Constants.Relationship.*;
import static TheFamily.Constants.Message.*;

import java.util.ArrayList;
import java.util.List;


public class Service
{
    private static final String FEMALE = "Female";

    private Member familyHead ;

    /**
     * Add head of the family
     * name   - name of the member
     * gender - gender
     */
    public void addFamilyHead(String name, String gender) {
        Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
        this.familyHead = new Member(name, g, null, null);
    }

    public void addSpouse(String memberName, String spouseName, String gender) {
        Member member = searchMember(familyHead, memberName);
        if (member != null && member.spouse == null) {
            Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
            Member sp = new Member(spouseName, g, null, null);
            sp.addSpouse(member);
            member.addSpouse(sp);
        }
    }

    public String addchild(String motherName, String childName, String gender) {
        String result;
        Member member = searchMember(familyHead, motherName);
        if (member == null) {
            result = PERSON_NOT_FOUND;
        } else if (childName == null || gender == null) {
            result = CHILD_ADDITION_FAILED;
        } else if (member.gender == Gender.Female) {
            Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
            Member child = new Member(childName, g, member.spouse, member);
            member.addChild(child);
            result = CHILD_ADDITION_SUCCEEDED;
        } else {
            result = CHILD_ADDITION_FAILED;
        }
        return result;
    }
    public String getRelationship(String person, String relationship) {

        String relations;
        Member member = searchMember(familyHead, person);
        if (member == null) {
            relations = PERSON_NOT_FOUND;
        } else if (relationship == null) {
            relations = PROVIDE_VALID_RELATION;
        } else {
            relations = getRelationship(member, relationship);
        }

        return relations;

    }

    private String getRelationship(Member member, String relationship) {
        String relations = "";
        switch (relationship) {

            case DAUGHTER:
                relations = member.searchChild(Gender.Female);
                break;

            case SON:
                relations = member.searchChild(Gender.Male);
                break;

            case SIBLINGS:
                relations = member.searchSiblings();
                break;

            case SISTER_IN_LAW:
                relations = searchInLaws(member, Gender.Female);
                break;

            case BROTHER_IN_LAW:
                relations = searchInLaws(member, Gender.Male);
                break;

            case MATERNAL_AUNT:
                if (member.mother != null)
                    relations = member.mother.searchAuntOrUncle(Gender.Female);
                break;

            case PATERNAL_AUNT:
                if (member.father != null)
                    relations = member.father.searchAuntOrUncle(Gender.Female);
                break;

            case MATERNAL_UNCLE:
                if (member.mother != null)
                    relations = member.mother.searchAuntOrUncle(Gender.Male);
                break;

            case PATERNAL_UNCLE:
                if (member.father != null)
                    relations = member.father.searchAuntOrUncle(Gender.Male);
                break;

            default:
                relations = NOT_YET_IMPLEMENTED;
                break;
        }

        return ("".equals(relations)) ? NONE : relations;

    }

    private String searchInLaws(Member member, Gender gender) {
        String personName = member.name;
        StringBuilder sb = new StringBuilder("");
        String res = "";

        if (member.spouse != null && member.spouse.mother != null)
        {
            for(Member i : member.spouse.mother.children)
            {
                if(i.gender.equals(gender))
                {
                    if(i.name != member.name)
                    {
                        sb.append(i.name+" ");
                    }
                }
            }
        }
        if (member.mother != null)
        {
            for(Member i : member.mother.children)
            {
                if(!i.gender.equals(gender))
                {
                    if(i.name != member.name && i.spouse != null)
                    {
                        sb.append(i.spouse.name+" ");
                    }
                }
            }
        }

        return sb.toString().trim();
    }

    private Member searchMember(Member head, String memberName) {
        if (memberName == null || head == null) {
            return null;
        }

        Member member = null;
        if (memberName.equals(head.name)) {
            return head;
        } else if (head.spouse != null && memberName.equals(head.spouse.name)) {
            return head.spouse;
        }

        List<Member> childlist = new ArrayList<>();
        if (head.gender == Gender.Female) {
            childlist = head.children;
        } else if (head.spouse != null) {
            childlist = head.spouse.children;
        }

        for (Member m : childlist) {
            member = searchMember(m, memberName);
            if (member != null) {
                break;
            }
        }
        return member;
    }
}
