package mk.ukim.finki.wp.internships.exception;

import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;

public class IllegalInternshipStatusOperation extends BadRequestException {
    public IllegalInternshipStatusOperation(InternshipStatus from, InternshipStatus to) {
        super("Not permitted to move from " + from.name() + " to " + to.name() + " status");
    }
}
