package lt.krtrinkunas.twodayinternship.service;

import lt.krtrinkunas.twodayinternship.exception.AssignmentFailedException;
import lt.krtrinkunas.twodayinternship.model.Animal;
import lt.krtrinkunas.twodayinternship.model.Enclosure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    @Value("${HUGE_SIZE}")
    private int hugeSize;

    @Value("${LARGE_SIZE}")
    private int largeSize;

    @Value("${MEDIUM_SIZE}")
    private int mediumSize;

    @Value("${SMALL_SIZE}")
    private int smallSize;

    private final EnclosureService enclosureService;

    @Autowired
    public AssignmentService(EnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }

    /*
    A recursive search algorithm which finds all valid combinations of animals
    for a given enclosure, assigns one of those combinations and moves on
    to the next enclosure using continueAssignment() function
    */
    public List<Animal> startAssignment(List<Animal> animalList) {
        for (Animal animal : animalList) {
            animal.setEnclosure(null);
        }
        List<Enclosure> availableEnclosures = enclosureService.readEnclosures();
        List<String> sizeOrder = List.of("Huge", "Large", "Medium", "Small");
        availableEnclosures.sort(Comparator.comparingInt(enclosure -> sizeOrder.indexOf(enclosure.getSize())));
        List<Animal> availableAnimals = new ArrayList<>(animalList);

        if (availableAnimals.isEmpty()) {
            return animalList;
        } else if (availableEnclosures.isEmpty()) {
            return null;
        }

        List<Animal> continuedAssignment = getAnimals(animalList, availableAnimals, availableEnclosures);
        if (continuedAssignment != null) return continuedAssignment;
        else throw new AssignmentFailedException();
    }

    private List<Animal> continueAssignment (List<Animal> animalList, List<Animal> availableAnimals, List<Enclosure> availableEnclosures) {
        if (availableAnimals.isEmpty()) {
            return animalList;
        } else if (availableEnclosures.isEmpty()) {
            return null;
        }

        List<Animal> continuedAssignment = getAnimals(animalList, availableAnimals, availableEnclosures);
        if (continuedAssignment != null) return continuedAssignment;
        return null;
    }

    private List<Animal> getAnimals(List<Animal> animalList, List<Animal> availableAnimals, List<Enclosure> availableEnclosures) {
        List<Enclosure> availableEnclosuresCopy = new ArrayList<>(availableEnclosures);
        Enclosure currentEnclosure = availableEnclosuresCopy.remove(0);

        int enclosureSize = getEnclosureSize(currentEnclosure);

        List<List<Animal>> sortedHerbAnimals = findBiggestHerbivoreGroup(availableAnimals, enclosureSize);
        List<List<Animal>> sortedCarnAnimals = findTwoCarnivoreGroup(availableAnimals, enclosureSize);
        List<Animal> biggestCarnAnimal = findSingleCarnivore(availableAnimals, enclosureSize);

        List<List<Animal>> candidateAnimals = new ArrayList<>();
        if (biggestCarnAnimal != null) {
            candidateAnimals.add(biggestCarnAnimal);
        }
        candidateAnimals.addAll(sortedHerbAnimals);
        candidateAnimals.addAll(sortedCarnAnimals);

        for (List<Animal> currentCandidateList : candidateAnimals) {
            for (Animal candidateAnimal : currentCandidateList) {
                availableAnimals.remove(candidateAnimal);
                int i = animalList.indexOf(candidateAnimal);
                Animal a = animalList.get(i);
                a.setEnclosure(currentEnclosure);
                animalList.set(i, a);
            }

            List<Animal> continuedAssignment = continueAssignment(animalList, availableAnimals, availableEnclosuresCopy);

            if (continuedAssignment == null) {
                for (Animal candidateAnimal : currentCandidateList) {
                    availableAnimals.add(candidateAnimal);
                    int i = animalList.indexOf(candidateAnimal);
                    Animal a = animalList.get(i);
                    a.setEnclosure(null);
                    animalList.set(i, a);
                }
            } else {
                return continuedAssignment;
            }
        }
        return null;
    }

    private int getEnclosureSize(Enclosure enclosure) {
        return switch (enclosure.getSize()) {
            case "Huge" -> hugeSize;
            case "Large" -> largeSize;
            case "Medium" -> mediumSize;
            case "Small" -> smallSize;
            default -> -1;
        };
    }

    private static List<List<Animal>> findBiggestHerbivoreGroup(List<Animal> animals, int targetAmount) {
        List<Animal> herbivores = animals.stream()
                .filter(animal -> animal.getFood().equals("Herbivore"))
                .collect(Collectors.toList());

        return findClosestGroups(herbivores, targetAmount);
    }

    private static List<Animal> findSingleCarnivore(List<Animal> animals, int targetAmount) {
        List<Animal> carnivores = animals.stream()
                .filter(animal -> animal.getFood().equals("Carnivore"))
                .collect(Collectors.toList());
        Animal closestCarnivore = findClosestAnimal(carnivores, targetAmount);

        if(closestCarnivore == null) {
            return null;
        }
        List<Animal> closestCarnivoreList = new ArrayList<>();
        closestCarnivoreList.add(closestCarnivore);

        return closestCarnivoreList;
    }

    private static List<List<Animal>> findTwoCarnivoreGroup(List<Animal> animals, int targetAmount) {
        List<Animal> carnivores = animals.stream()
                .filter(animal -> animal.getFood().equals("Carnivore"))
                .collect(Collectors.toList());

        return findClosestGroups(carnivores, targetAmount, 2);
    }

    private static List<List<Animal>> findClosestGroups(List<Animal> animals, int targetAmount) {
        List<List<Animal>> closestGroups = new ArrayList<>();
        Set<Set<Animal>> visitedGroups = new HashSet<>();

        for (int i = 0; i < animals.size(); i++) {
            List<Animal> currentGroup = new ArrayList<>();
            int currentSum = animals.get(i).getAmount();
            int remainingAmount = targetAmount - currentSum;
            if (remainingAmount >= 0) {
                currentGroup.add(animals.get(i));
                for (int j = i + 1; j < animals.size(); j++) {
                    int amount = animals.get(j).getAmount();
                    if (amount <= remainingAmount) {
                        currentGroup.add(animals.get(j));
                        currentSum += amount;
                        remainingAmount = targetAmount - currentSum;
                        if (remainingAmount == 0) {
                            Set<Animal> animalSet = new HashSet<>(currentGroup);
                            if (!isSubset(animalSet, visitedGroups)) {
                                closestGroups.add(new ArrayList<>(currentGroup));
                                visitedGroups.add(animalSet);
                            }
                            break;
                        }
                    }
                }
            }


            Set<Animal> animalSet = new HashSet<>(currentGroup);
            if (!isSubset(animalSet, visitedGroups)) {
                closestGroups.add(new ArrayList<>(currentGroup));
                visitedGroups.add(animalSet);
            }
        }

        closestGroups.sort(Comparator.comparingInt(group -> getTotalAmount((List<Animal>) group)).reversed());
        return closestGroups;
    }

    private static List<List<Animal>> findClosestGroups(List<Animal> animals, int targetAmount, int size) {
        List<List<Animal>> closestGroups = new ArrayList<>();
        Set<Set<Animal>> visitedGroups = new HashSet<>();

        for (int i = 0; i < animals.size(); i++) {
            List<Animal> currentGroup = new ArrayList<>();
            int currentSum = animals.get(i).getAmount();
            int remainingAmount = targetAmount - currentSum;
            if (remainingAmount >= 0) {
                currentGroup.add(animals.get(i));
                for (int j = i + 1; j < animals.size(); j++) {
                    int amount = animals.get(j).getAmount();
                    if (amount <= remainingAmount && currentGroup.size() < size) {
                        currentGroup.add(animals.get(j));
                        currentSum += amount;
                        remainingAmount = targetAmount - currentSum;
                        if (remainingAmount == 0) {
                            Set<Animal> animalSet = new HashSet<>(currentGroup);
                            if (!isSubset(animalSet, visitedGroups)) {
                                closestGroups.add(new ArrayList<>(currentGroup));
                                visitedGroups.add(animalSet);
                            }
                            break;
                        }
                    }
                }
            }

            Set<Animal> animalSet = new HashSet<>(currentGroup);
            if (!isSubset(animalSet, visitedGroups)) {
                closestGroups.add(new ArrayList<>(currentGroup));
                visitedGroups.add(animalSet);
            }
        }

        closestGroups.sort(Comparator.comparingInt(group -> getTotalAmount((List<Animal>) group)).reversed());
        return closestGroups;
    }


    private static boolean isSubset(Set<Animal> group, Set<Set<Animal>> visitedGroups) {
        for (Set<Animal> visitedGroup : visitedGroups) {
            if (visitedGroup.containsAll(group)) {
                return true;
            }
        }
        return false;
    }

    private static int getTotalAmount(List<Animal> animals) {
        return animals.stream().mapToInt(Animal::getAmount).sum();
    }

    private static Animal findClosestAnimal(List<Animal> animals, int targetAmount) {
        Animal closestAnimal = null;

        for (Animal animal : animals) {
            if (closestAnimal == null || Math.abs(targetAmount - animal.getAmount()) < Math.abs(targetAmount - closestAnimal.getAmount()))
                closestAnimal = animal;
        }

        return closestAnimal;
    }

}
