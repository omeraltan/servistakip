import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MetodTemelComponentsPage from './metod-temel.page-object';
import MetodTemelUpdatePage from './metod-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('MetodTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let metodTemelComponentsPage: MetodTemelComponentsPage;
  let metodTemelUpdatePage: MetodTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    metodTemelComponentsPage = new MetodTemelComponentsPage();
    metodTemelComponentsPage = await metodTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load MetodTemels', async () => {
    expect(await metodTemelComponentsPage.title.getText()).to.match(/Metod Temels/);
    expect(await metodTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete MetodTemels', async () => {
    const beforeRecordsCount = (await isVisible(metodTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(metodTemelComponentsPage.table);
    metodTemelUpdatePage = await metodTemelComponentsPage.goToCreateMetodTemel();
    await metodTemelUpdatePage.enterData();
    expect(await isVisible(metodTemelUpdatePage.saveButton)).to.be.false;

    expect(await metodTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(metodTemelComponentsPage.table);
    await waitUntilCount(metodTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await metodTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await metodTemelComponentsPage.deleteMetodTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(metodTemelComponentsPage.records, beforeRecordsCount);
      expect(await metodTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(metodTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
